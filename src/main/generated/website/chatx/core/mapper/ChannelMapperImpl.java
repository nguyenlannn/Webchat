package website.chatx.core.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import website.chatx.core.entities.ChannelEntity;
import website.chatx.dto.res.entity.ChannelEntityRes;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-27T08:48:28+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Amazon.com Inc.)"
)
@Component
public class ChannelMapperImpl extends ChannelMapper {

    @Override
    public ChannelEntityRes toChannelRes(ChannelEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ChannelEntityRes.ChannelEntityResBuilder<?, ?> channelEntityRes = ChannelEntityRes.builder();

        channelEntityRes.id( entity.getId() );
        channelEntityRes.createdAt( map( entity.getCreatedAt() ) );
        channelEntityRes.updatedAt( map( entity.getUpdatedAt() ) );
        channelEntityRes.createdBy( entity.getCreatedBy() );
        channelEntityRes.updatedBy( entity.getUpdatedBy() );
        channelEntityRes.ownerId( entity.getOwnerId() );
        channelEntityRes.name( entity.getName() );
        channelEntityRes.avatarUrl( entity.getAvatarUrl() );
        channelEntityRes.type( entity.getType() );

        return channelEntityRes.build();
    }
}
