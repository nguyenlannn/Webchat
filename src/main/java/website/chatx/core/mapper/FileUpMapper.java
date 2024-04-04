package website.chatx.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import website.chatx.core.base.BaseCustomMapper;
import website.chatx.dto.res.entity.FileUpEntityRes;
import website.chatx.core.entities.FileUpEntity;

@Mapper(componentModel = "spring")
public abstract class FileUpMapper implements BaseCustomMapper {
    @Mapping(target = "user", ignore = true)
    public abstract FileUpEntityRes toFileUpRes(FileUpEntity entity);
}
