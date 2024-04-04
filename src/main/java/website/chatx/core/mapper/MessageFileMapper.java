package website.chatx.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import website.chatx.core.base.BaseCustomMapper;
import website.chatx.core.entities.MessageFileEntity;
import website.chatx.dto.res.entity.MessageFileEntityRes;

@Mapper(componentModel = "spring")
public abstract class MessageFileMapper implements BaseCustomMapper {
    @Mapping(target = "sender", ignore = true)
    @Mapping(target = "message", ignore = true)
    @Mapping(target = "channel", ignore = true)
    public abstract MessageFileEntityRes toMessageFileRes(MessageFileEntity entity);
}
