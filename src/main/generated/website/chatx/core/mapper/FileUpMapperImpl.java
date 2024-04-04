package website.chatx.core.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import website.chatx.core.entities.FileUpEntity;
import website.chatx.dto.res.entity.FileUpEntityRes;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-27T08:48:28+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Amazon.com Inc.)"
)
@Component
public class FileUpMapperImpl extends FileUpMapper {

    @Override
    public FileUpEntityRes toFileUpRes(FileUpEntity entity) {
        if ( entity == null ) {
            return null;
        }

        FileUpEntityRes.FileUpEntityResBuilder<?, ?> fileUpEntityRes = FileUpEntityRes.builder();

        fileUpEntityRes.id( entity.getId() );
        fileUpEntityRes.createdAt( map( entity.getCreatedAt() ) );
        fileUpEntityRes.updatedAt( map( entity.getUpdatedAt() ) );
        fileUpEntityRes.createdBy( entity.getCreatedBy() );
        fileUpEntityRes.updatedBy( entity.getUpdatedBy() );
        fileUpEntityRes.url( entity.getUrl() );
        fileUpEntityRes.name( entity.getName() );
        fileUpEntityRes.contentType( entity.getContentType() );
        fileUpEntityRes.size( entity.getSize() );

        return fileUpEntityRes.build();
    }
}
