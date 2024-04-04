package website.chatx.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import website.chatx.core.entities.MessageEntity;
import website.chatx.core.entities.MessageFileEntity;

import java.util.List;

@Repository
public interface MessageFileJpaRepository extends JpaRepository<MessageFileEntity, String> {

    List<MessageFileEntity> findByMessage(MessageEntity messageEntity);

    void deleteByMessage(MessageEntity messageEntity);
}
