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
public class ListChannelRss {
    private String id;
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

    private String currentMessageId;
    private String currentMessageContent;
    private LocalDateTime currentMessageCreatedAt;
    private LocalDateTime currentMessageUpdatedAt;

    private String senderCurrentMessageId;
    private String senderCurrentMessageEmail;
    private String senderCurrentMessageName;
    private String senderCurrentMessageAvatarUrl;
}
