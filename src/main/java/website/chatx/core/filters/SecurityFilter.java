package website.chatx.core.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import website.chatx.core.common.CommonAuthContext;
import website.chatx.core.common.CommonResponse;
import website.chatx.core.entities.UserEntity;
import website.chatx.repositories.jpa.UserJpaRepository;

import java.io.IOException;
import java.util.regex.Pattern;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Log4j2
@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final UserJpaRepository userRepository;
    private final CommonAuthContext authContext;

    @Value("${application.jwt.secret-key}")
    private String SECRET_KEY;

//    @Value("${application.api-key}")
//    private String API_KEY;

    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        CommonResponse res = CommonResponse.builder()
                .success(false)
                .errorCode(HttpStatus.UNAUTHORIZED.value())
                .build();
        response.setContentType("application/json");

        if (Pattern.compile("^(/swagger-ui.*)|(/v3/api-docs.*)$")
                .matcher(request.getServletPath()).find()) {
            filterChain.doFilter(request, response);
        }
//        else if (!StringUtils.hasText(request.getHeader("api-key"))) {
//            res.setMessage("api-key is empty");
//            new ObjectMapper().writeValue(response.getOutputStream(), res);
//        }
//        else if (!request.getHeader("api-key").equals(API_KEY)) {
//            res.setMessage("api-key is wrong");
//            new ObjectMapper().writeValue(response.getOutputStream(), res);
//        }
        else if (Pattern.compile("^/basic/.*$")
                .matcher(request.getServletPath()).find()) {
            filterChain.doFilter(request, response);
        } else if (!StringUtils.hasText(request.getHeader(AUTHORIZATION)) && !request.getRequestURI().startsWith("/api/v1/wsjs")) {
            res.setMessage("token is empty");
            new ObjectMapper().writeValue(response.getOutputStream(), res);
        } else {
            try {
                String token;
                if (request.getRequestURI().startsWith("/api/v1/wsjs")) {
                    token = request.getParameter("token").substring("Bearer ".length());
                } else {
                    token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
                }
                Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);

                UserEntity userEntity = userRepository.findById(decodedJWT.getSubject()).orElse(null);
                assert userEntity != null;
//                SecurityContextHolder.getContext().setAuthentication(
//                        new UsernamePasswordAuthenticationToken(null, null, userEntity.getRoles().stream()
//                                .map(role -> new SimpleGrantedAuthority(role.name()))
//                                .collect(Collectors.toList())));
                authContext.set(userEntity);
                filterChain.doFilter(request, response);
            } catch (Exception exception) {
                res.setMessage(exception.getMessage());
                new ObjectMapper().writeValue(response.getOutputStream(), res);
            }
        }
    }
}
