package website.chatx.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import website.chatx.core.base.BaseCustomMapper;
import website.chatx.dto.res.entity.MessageEntityRes;
import website.chatx.core.entities.MessageEntity;

@Mapper(componentModel = "spring")
public abstract class MessageMapper implements BaseCustomMapper {
    @Mapping(target = "messageFiles", ignore = true)
    @Mapping(target = "sender", ignore = true)
    @Mapping(target = "channel", ignore = true)
    public abstract MessageEntityRes toMessageRes(MessageEntity entity);
}
