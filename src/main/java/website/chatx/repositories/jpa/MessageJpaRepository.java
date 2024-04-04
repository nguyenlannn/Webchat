package website.chatx.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import website.chatx.core.entities.MessageEntity;

@Repository
public interface MessageJpaRepository extends JpaRepository<MessageEntity, String> {
}
