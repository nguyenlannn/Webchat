package website.chatx.dto.res.channel;

import lombok.*;
import website.chatx.core.enums.ChannelTypeEnum;
import website.chatx.core.enums.UserChannelStatusEnum;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailChannelRes {
    private String id;
    private String ownerId;
    private String name;
    private String email;
    private String avatarUrl;
    private ChannelTypeEnum type;
    private Long createdAt;
    private Long updatedAt;

    private UserChannelStatusEnum status;
    private UserChannelStatusEnum friendStatus;
}
