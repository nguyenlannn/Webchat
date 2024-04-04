package website.chatx.async;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import website.chatx.core.entities.ChannelEntity;
import website.chatx.core.entities.MessageEntity;
import website.chatx.core.entities.MessageFileEntity;
import website.chatx.core.entities.UserChannelEntity;
import website.chatx.core.enums.ChannelTypeEnum;
import website.chatx.core.enums.UserChannelStatusEnum;
import website.chatx.dto.res.channel.list.CurrentMessageRes;
import website.chatx.dto.res.channel.list.FileRes;
import website.chatx.dto.res.channel.list.ListChannelRes;
import website.chatx.dto.res.channel.list.SenderRes;
import website.chatx.repositories.jpa.ChannelJpaRepository;
import website.chatx.repositories.jpa.MessageFileJpaRepository;
import website.chatx.repositories.jpa.MessageJpaRepository;
import website.chatx.repositories.jpa.UserChannelJpaRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PushMessageAsync {

    private final MessageJpaRepository messageJpaRepository;
    private final ChannelJpaRepository channelJpaRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserChannelJpaRepository userChannelJpaRepository;
    private final MessageFileJpaRepository messageFileJpaRepository;

    @Async
    public void pushNotify(String messageId, String channelId, String type, String meId) {

        ChannelEntity channelEntity = channelJpaRepository.findById(channelId).orElse(null);
        if (channelEntity != null) {

            MessageEntity messageEntity = messageJpaRepository.findById(messageId).orElse(null);

            List<MessageFileEntity> messageFileEntities = new ArrayList<>();
            if (messageEntity != null) {
                messageFileEntities = messageFileJpaRepository.findByMessage(messageEntity);
            }

            List<UserChannelEntity> userChannelEntity = userChannelJpaRepository.findByChannel(channelEntity);

            UserChannelEntity friend = new UserChannelEntity();
            if (channelEntity.getType() == ChannelTypeEnum.FRIEND) {
                for (UserChannelEntity o : userChannelEntity) {
                    if (!o.getUser().getId().equals(meId)) {
                        friend = o;
                    }
                }
            }

            ListChannelRes res = ListChannelRes.builder()
                    .id(channelEntity.getId())
                    .name(channelEntity.getType() == ChannelTypeEnum.FRIEND
                            ? friend.getUser().getName() : channelEntity.getName()
                    )
                    .avatarUrl(channelEntity.getType() == ChannelTypeEnum.FRIEND
                            ? friend.getUser().getAvatarUrl() : channelEntity.getAvatarUrl())
                    .type(channelEntity.getType())
                    .createdAt(Timestamp.valueOf(channelEntity.getCreatedAt()).getTime())
                    .updatedAt(Timestamp.valueOf(channelEntity.getUpdatedAt()).getTime())
                    .currentMessage(CurrentMessageRes.builder()
                            .id(messageId)
                            .type(type)
                            .content(messageEntity != null ? messageEntity.getContent() : null)
                            .createdAt(messageEntity != null ? Timestamp.valueOf(messageEntity.getCreatedAt()).getTime() : null)
                            .updatedAt(messageEntity != null ? Timestamp.valueOf(messageEntity.getUpdatedAt()).getTime() : null)
                            .files(messageEntity != null
                                    ? messageFileEntities.stream()
                                    .map(o -> FileRes.builder()
                                            .id(o.getId())
                                            .name(o.getName())
                                            .url(o.getUrl())
                                            .contentType(o.getContentType())
                                            .size(o.getSize())
                                            .createdAt(Timestamp.valueOf(o.getCreatedAt()).getTime())
                                            .updatedAt(Timestamp.valueOf(o.getUpdatedAt()).getTime())
                                            .build())
                                    .collect(Collectors.toList())
                                    : null
                            )
                            .sender(messageEntity != null
                                    ? SenderRes.builder()
                                    .id(messageEntity.getSender().getId())
                                    .email(messageEntity.getSender().getEmail())
                                    .name(messageEntity.getSender().getName())
                                    .avatarUrl(messageEntity.getSender().getAvatarUrl())
                                    .build()
                                    : null
                            )
                            .build())
                    .build();

            userChannelEntity.parallelStream()
                    .filter(o -> o.getStatus() == UserChannelStatusEnum.ACCEPT)
//                    .filter(o -> !o.getUser().getId().equals(meId))
                    .forEach(oo -> simpMessagingTemplate.convertAndSendToUser(oo.getUser().getId(), "/private", res));
        }
    }
}
