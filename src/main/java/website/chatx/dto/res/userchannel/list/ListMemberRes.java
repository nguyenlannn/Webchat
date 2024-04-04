package website.chatx.dto.res.userchannel.list;


import lombok.*;
import website.chatx.core.enums.UserChannelStatusEnum;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListMemberRes {
    private String id;
    private String email;
    private String name;
    private String avatarUrl;
    private UserChannelStatusEnum status;
    private UserChannelStatusEnum friendStatus;
}
