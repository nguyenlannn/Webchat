package website.chatx.dto.res.user;

import lombok.*;
import website.chatx.core.enums.UserChannelStatusEnum;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OneUserToAddFriendRes {
    private String id;
    private String email;
    private String name;
    private String avatarUrl;
    private UserChannelStatusEnum status;
}
