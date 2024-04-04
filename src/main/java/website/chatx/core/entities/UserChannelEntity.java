package website.chatx.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import website.chatx.core.base.BaseEntity;
import website.chatx.core.enums.UserChannelStatusEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "user_channel")
public class UserChannelEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private UserChannelStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private ChannelEntity channel;
}
