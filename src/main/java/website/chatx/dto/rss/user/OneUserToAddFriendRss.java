package website.chatx.dto.rss.user;

import lombok.*;
import website.chatx.core.enums.UserChannelStatusEnum;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OneUserToAddFriendRss {
    private String id;
    private String email;
    private String name;
    private String avatarUrl;
    private UserChannelStatusEnum status;
}
