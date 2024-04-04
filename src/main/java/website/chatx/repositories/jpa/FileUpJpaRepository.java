package website.chatx.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import website.chatx.core.entities.FileUpEntity;
import website.chatx.core.entities.UserEntity;

@Repository
public interface FileUpJpaRepository extends JpaRepository<FileUpEntity, String> {

    FileUpEntity findByIdAndUser(String id, UserEntity userEntity);
}
