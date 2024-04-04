package website.chatx.dto.req.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserReq {

    private String name;

    private String avatarUrl;

    private String newPassword;

    private String oldPassword;
}
