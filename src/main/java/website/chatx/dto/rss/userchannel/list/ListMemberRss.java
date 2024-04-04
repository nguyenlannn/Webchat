package website.chatx.dto.rss.userchannel.list;


import lombok.*;
import website.chatx.core.enums.UserChannelStatusEnum;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListMemberRss {
    private String id;
    private String email;
    private String name;
    private String avatarUrl;
    private UserChannelStatusEnum status;
}
