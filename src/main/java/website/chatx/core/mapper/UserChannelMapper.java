package website.chatx.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import website.chatx.core.base.BaseCustomMapper;
import website.chatx.dto.res.entity.UserChannelEntityRes;
import website.chatx.core.entities.UserChannelEntity;

@Mapper(componentModel = "spring")
public abstract class UserChannelMapper implements BaseCustomMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "channel", ignore = true)
    public abstract UserChannelEntityRes toUserChannelRes(UserChannelEntity entity);
}
