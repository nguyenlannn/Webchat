package website.chatx.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website.chatx.async.SendEmailAsync;
import website.chatx.core.entities.UserActivationCodeEntity;
import website.chatx.core.entities.UserEntity;
import website.chatx.core.exception.BusinessLogicException;
import website.chatx.dto.req.auth.ActiveUserReq;
import website.chatx.dto.req.auth.ResetPasswordReq;
import website.chatx.dto.req.auth.SignInReq;
import website.chatx.dto.req.auth.SignUpReq;
import website.chatx.dto.res.auth.SignInRes;
import website.chatx.repositories.jpa.UserActivationCodeJpaRepository;
import website.chatx.repositories.jpa.UserJpaRepository;
import website.chatx.service.AuthService;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserJpaRepository userJpaRepository;
    private final UserActivationCodeJpaRepository userActivationCodeJpaRepository;

    private final PasswordEncoder passwordEncoder;

    private final SendEmailAsync sendEmailAsync;

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    private final HttpServletRequest request;

    @Value("${application.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${application.jwt.access-token-age}")
    private Long ACCESS_TOKEN_AGE;

    private final Log LOGGER = LogFactory.getLog(getClass());

    @Override
    public void signUp(SignUpReq req) {
        UserEntity userEntity = userJpaRepository.findByEmail(req.getEmail());
        if (userEntity != null) {
            throw new BusinessLogicException(-1);
        }
        userEntity = UserEntity.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .name(req.getName())
                .isActivated(false)
                .build();
        userJpaRepository.save(userEntity);
        String verifyToken = RandomStringUtils.random(4, false, true);
        userActivationCodeJpaRepository.save(UserActivationCodeEntity.builder()
                .code(verifyToken)
                .user(userEntity)
                .build());
        sendEmailAsync.sendSimpleMessage(req.getEmail(), "Mã kích hoạt tài khoản", verifyToken);
    }

    @Override
    @Transactional(readOnly = true)
    public SignInRes signIn(SignInReq req) {
        try {
            daoAuthenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BusinessLogicException(-3);
        }
        UserEntity userEntity = userJpaRepository.findByEmail(req.getEmail());
        if (!userEntity.getIsActivated()) {
            throw new BusinessLogicException(-4);
        }
        return new SignInRes(JWT.create()
                .withSubject(userEntity.getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_AGE))
                .withIssuer(request.getRequestURL().toString())
                .sign(Algorithm.HMAC256(SECRET_KEY.getBytes())));
    }

    @Override
    public void active(ActiveUserReq req) {
        UserEntity userEntity = userJpaRepository.findByEmail(req.getEmail());
        if (userEntity == null) {
            throw new BusinessLogicException(-5);
        }
        if (!req.getVerifyToken().equals(userEntity.getUserActivationCodes().get(0).getCode())) {
            throw new BusinessLogicException(-6);
        }
        userEntity.setIsActivated(true);
        userJpaRepository.save(userEntity);
    }

    @Override
    public void resetPassword(ResetPasswordReq req) {
        UserEntity userEntity = userJpaRepository.findByEmail(req.getEmail());
        if (userEntity == null) {
            throw new BusinessLogicException(-7);
        }
        String newPassword = RandomStringUtils.random(5, "1234567890")
                + RandomStringUtils.random(1, "qwertyuiop")
                + RandomStringUtils.random(1, "ASDFGHJKLZ")
                + RandomStringUtils.random(1, "!@#$%^&*()");
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userJpaRepository.save(userEntity);
        sendEmailAsync.sendSimpleMessage(userEntity.getEmail(), "Mật khẩu mới", newPassword);
    }
}