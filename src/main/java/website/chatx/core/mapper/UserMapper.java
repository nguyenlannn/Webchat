package website.chatx.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import website.chatx.core.base.BaseCustomMapper;
import website.chatx.core.entities.UserEntity;
import website.chatx.dto.res.entity.UserEntityRes;
import website.chatx.dto.res.user.OneUserToAddFriendRes;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements BaseCustomMapper {
    @Mapping(target = "userActivationCodes", ignore = true)
    @Mapping(target = "userChannels", ignore = true)
    @Mapping(target = "messages", ignore = true)
    @Mapping(target = "fileUps", ignore = true)
    @Mapping(target = "messageFiles", ignore = true)
    public abstract UserEntityRes toUserEntityRes(UserEntity entity);

    public abstract OneUserToAddFriendRes toUserToAddFriendRes(UserEntity entity);
}
