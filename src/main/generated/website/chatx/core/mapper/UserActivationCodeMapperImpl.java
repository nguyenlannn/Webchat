package website.chatx.core.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import website.chatx.core.entities.UserActivationCodeEntity;
import website.chatx.dto.res.entity.UserActivationCodeEntityRes;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-27T08:48:28+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Amazon.com Inc.)"
)
@Component
public class UserActivationCodeMapperImpl extends UserActivationCodeMapper {

    @Override
    public UserActivationCodeEntityRes toUserActivationCodeRes(UserActivationCodeEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserActivationCodeEntityRes.UserActivationCodeEntityResBuilder<?, ?> userActivationCodeEntityRes = UserActivationCodeEntityRes.builder();

        userActivationCodeEntityRes.id( entity.getId() );
        userActivationCodeEntityRes.createdAt( map( entity.getCreatedAt() ) );
        userActivationCodeEntityRes.updatedAt( map( entity.getUpdatedAt() ) );
        userActivationCodeEntityRes.createdBy( entity.getCreatedBy() );
        userActivationCodeEntityRes.updatedBy( entity.getUpdatedBy() );
        userActivationCodeEntityRes.code( entity.getCode() );

        return userActivationCodeEntityRes.build();
    }
}
