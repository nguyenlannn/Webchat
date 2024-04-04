package website.chatx.core.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import website.chatx.core.entities.MessageEntity;
import website.chatx.dto.res.entity.MessageEntityRes;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-27T08:48:28+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Amazon.com Inc.)"
)
@Component
public class MessageMapperImpl extends MessageMapper {

    @Override
    public MessageEntityRes toMessageRes(MessageEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MessageEntityRes.MessageEntityResBuilder<?, ?> messageEntityRes = MessageEntityRes.builder();

        messageEntityRes.id( entity.getId() );
        messageEntityRes.createdAt( map( entity.getCreatedAt() ) );
        messageEntityRes.updatedAt( map( entity.getUpdatedAt() ) );
        messageEntityRes.createdBy( entity.getCreatedBy() );
        messageEntityRes.updatedBy( entity.getUpdatedBy() );
        messageEntityRes.content( entity.getContent() );

        return messageEntityRes.build();
    }
}
