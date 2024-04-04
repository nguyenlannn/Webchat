package website.chatx.core.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import website.chatx.core.entities.UserEntity;
import website.chatx.dto.res.entity.UserEntityRes;
import website.chatx.dto.res.user.OneUserToAddFriendRes;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-27T08:48:28+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl extends UserMapper {

    @Override
    public UserEntityRes toUserEntityRes(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserEntityRes.UserEntityResBuilder<?, ?> userEntityRes = UserEntityRes.builder();

        userEntityRes.id( entity.getId() );
        userEntityRes.createdAt( map( entity.getCreatedAt() ) );
        userEntityRes.updatedAt( map( entity.getUpdatedAt() ) );
        userEntityRes.createdBy( entity.getCreatedBy() );
        userEntityRes.updatedBy( entity.getUpdatedBy() );
        userEntityRes.email( entity.getEmail() );
        userEntityRes.name( entity.getName() );
        userEntityRes.gender( entity.getGender() );
        userEntityRes.phone( entity.getPhone() );
        userEntityRes.address( entity.getAddress() );
        userEntityRes.avatarUrl( entity.getAvatarUrl() );
        userEntityRes.isActivated( entity.getIsActivated() );

        return userEntityRes.build();
    }

    @Override
    public OneUserToAddFriendRes toUserToAddFriendRes(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        OneUserToAddFriendRes.OneUserToAddFriendResBuilder oneUserToAddFriendRes = OneUserToAddFriendRes.builder();

        oneUserToAddFriendRes.id( entity.getId() );
        oneUserToAddFriendRes.email( entity.getEmail() );
        oneUserToAddFriendRes.name( entity.getName() );
        oneUserToAddFriendRes.avatarUrl( entity.getAvatarUrl() );

        return oneUserToAddFriendRes.build();
    }
}
