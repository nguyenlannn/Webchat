package website.chatx.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import website.chatx.core.entities.ChannelEntity;

import java.util.Optional;

@Repository
public interface ChannelJpaRepository extends JpaRepository<ChannelEntity, String> {

    @Query(nativeQuery = true,
            value = """
                    select c1.*
                    from channel c1
                        join user_channel uc1
                            on (c1.id = uc1.channel_id
                                and uc1.user_id = ?1
                                and uc1.channel_id = ?2
                                and uc1.status = 'ACCEPT'
                                and c1.type = 'GROUP'
                            )
                    """)
    Optional<ChannelEntity> findByMyIdAndGroupId(String userId, String channelId);

    @Query(nativeQuery = true,
            value = """
                    select c1.*
                    from channel c1
                        join user_channel uc1
                            on (c1.id = uc1.channel_id
                                and uc1.user_id = ?1
                                and uc1.channel_id = ?2
                                and uc1.status = 'ACCEPT'
                            )
                    """)
    Optional<ChannelEntity> findByMyIdAndChannel1Id(String userId, String channelId);

    @Query(nativeQuery = true,
            value = """
                    select c1.*
                             from channel c1
                                 join user_channel uc1
                                     on (c1.id = uc1.channel_id
                                         and uc1.user_id = ?1
                                         and uc1.status = 'ACCEPT'
                                         and c1.type = 'FRIEND'
                                         )
                                 join user_channel uc2
                                     on (c1.id = uc2.channel_id
                                         and uc2.user_id = ?2
                                         and uc2.user_id != ?1
                                         and uc2.status = 'ACCEPT'
                                         and c1.type = 'FRIEND'
                                         )
                     """)
    Optional<ChannelEntity> checkFriend(String myId, String theirId);
}
