package website.chatx.core.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import website.chatx.core.entities.UserChannelEntity;
import website.chatx.dto.res.entity.UserChannelEntityRes;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-27T08:48:28+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Amazon.com Inc.)"
)
@Component
public class UserChannelMapperImpl extends UserChannelMapper {

    @Override
    public UserChannelEntityRes toUserChannelRes(UserChannelEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserChannelEntityRes.UserChannelEntityResBuilder<?, ?> userChannelEntityRes = UserChannelEntityRes.builder();

        userChannelEntityRes.id( entity.getId() );
        userChannelEntityRes.createdAt( map( entity.getCreatedAt() ) );
        userChannelEntityRes.updatedAt( map( entity.getUpdatedAt() ) );
        userChannelEntityRes.createdBy( entity.getCreatedBy() );
        userChannelEntityRes.updatedBy( entity.getUpdatedBy() );
        userChannelEntityRes.status( entity.getStatus() );

        return userChannelEntityRes.build();
    }
}
