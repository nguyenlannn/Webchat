package website.chatx.dto.rss.channel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import website.chatx.core.enums.ChannelTypeEnum;
import website.chatx.core.enums.UserChannelStatusEnum;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailChannelRss {
    private String id;
    private String ownerId;
    private String name;
    private String avatarUrl;
    private ChannelTypeEnum type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private UserChannelStatusEnum status;
    private UserChannelStatusEnum friendStatus;

    private String friendId;
    private String friendEmail;
    private String friendName;
    private String friendAvatarUrl;
}
