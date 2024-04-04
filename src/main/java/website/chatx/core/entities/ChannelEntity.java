package website.chatx.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import website.chatx.core.base.BaseEntity;
import website.chatx.core.enums.ChannelTypeEnum;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "channel")
public class ChannelEntity extends BaseEntity {

    private String ownerId;

    private String name;

    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    private ChannelTypeEnum type;

    @OneToMany(mappedBy = "channel")
    private List<UserChannelEntity> userChannels;

    @OneToMany(mappedBy = "channel")
    private List<MessageFileEntity> messageFiles;

    @OneToMany(mappedBy = "channel")
    private List<MessageEntity> messages;
}
