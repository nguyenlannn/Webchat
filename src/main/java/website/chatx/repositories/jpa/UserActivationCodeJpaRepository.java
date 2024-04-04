package website.chatx.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import website.chatx.core.entities.UserActivationCodeEntity;

public interface UserActivationCodeJpaRepository extends JpaRepository<UserActivationCodeEntity, String> {
}
