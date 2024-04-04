package website.chatx.core.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import website.chatx.core.entities.MessageFileEntity;
import website.chatx.dto.res.entity.MessageFileEntityRes;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-27T08:48:28+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Amazon.com Inc.)"
)
@Component
public class MessageFileMapperImpl extends MessageFileMapper {

    @Override
    public MessageFileEntityRes toMessageFileRes(MessageFileEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MessageFileEntityRes.MessageFileEntityResBuilder<?, ?> messageFileEntityRes = MessageFileEntityRes.builder();

        messageFileEntityRes.id( entity.getId() );
        messageFileEntityRes.createdAt( map( entity.getCreatedAt() ) );
        messageFileEntityRes.updatedAt( map( entity.getUpdatedAt() ) );
        messageFileEntityRes.createdBy( entity.getCreatedBy() );
        messageFileEntityRes.updatedBy( entity.getUpdatedBy() );
        messageFileEntityRes.name( entity.getName() );
        messageFileEntityRes.url( entity.getUrl() );
        messageFileEntityRes.contentType( entity.getContentType() );
        messageFileEntityRes.size( entity.getSize() );

        return messageFileEntityRes.build();
    }
}
