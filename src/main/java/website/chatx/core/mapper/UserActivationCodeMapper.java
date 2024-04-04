package website.chatx.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import website.chatx.core.base.BaseCustomMapper;
import website.chatx.dto.res.entity.UserActivationCodeEntityRes;
import website.chatx.core.entities.UserActivationCodeEntity;

@Mapper(componentModel = "spring")
public abstract class UserActivationCodeMapper implements BaseCustomMapper {
    @Mapping(target = "user", ignore = true)
    public abstract UserActivationCodeEntityRes toUserActivationCodeRes(UserActivationCodeEntity entity);
}
