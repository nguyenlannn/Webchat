package website.chatx.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import website.chatx.core.entities.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmail(String email);

    @Query(nativeQuery = true,
            value = """
                    select u1.* from user u1 where u1.id = ?1 and u1.is_activated = 1
                    """)
    Optional<UserEntity> findByIdAndActivatedTrue(String id);

    @Query(nativeQuery = true,
            value = """
                    select u1.* from user u1 where u1.id in (?1) and u1.is_activated = 1
                    """)
    List<UserEntity> findByListIdAndActivatedTrue(List<String> id);
}
