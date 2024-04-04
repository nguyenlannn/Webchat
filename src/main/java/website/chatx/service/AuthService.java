package website.chatx.service;

import website.chatx.dto.req.auth.ActiveUserReq;
import website.chatx.dto.req.auth.ResetPasswordReq;
import website.chatx.dto.req.auth.SignInReq;
import website.chatx.dto.req.auth.SignUpReq;
import website.chatx.dto.res.auth.SignInRes;

public interface AuthService {

    void signUp(SignUpReq req);

    SignInRes signIn(SignInReq req);

    void active(ActiveUserReq req);

    void resetPassword(ResetPasswordReq req);
}
