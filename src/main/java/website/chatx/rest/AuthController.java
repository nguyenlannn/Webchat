package website.chatx.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import website.chatx.core.common.CommonResponse;
import website.chatx.dto.req.auth.ActiveUserReq;
import website.chatx.dto.req.auth.ResetPasswordReq;
import website.chatx.dto.req.auth.SignInReq;
import website.chatx.dto.req.auth.SignUpReq;
import website.chatx.service.AuthService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/basic/auths")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> signUp(@RequestBody @Valid SignUpReq req) {
        authService.signUp(req);
        return CommonResponse.success("");
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse> signIn(@RequestBody @Valid SignInReq req) {
        return CommonResponse.success(authService.signIn(req));
    }

    @PostMapping("/active")
    public ResponseEntity<CommonResponse> active(@RequestBody @Valid ActiveUserReq req) {
        authService.active(req);
        return CommonResponse.success("");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<CommonResponse> resetPassword(@RequestBody @Valid ResetPasswordReq req) {
        authService.resetPassword(req);
        return CommonResponse.success("");
    }
}
