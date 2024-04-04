package website.chatx.dto.res.auth;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRes {
    private String token;
}
