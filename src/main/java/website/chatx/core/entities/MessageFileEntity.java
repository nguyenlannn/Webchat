package website.chatx.core.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import website.chatx.core.base.BaseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "message_file")
public class MessageFileEntity extends BaseEntity {

    private String name;

    private String url;

    private String contentType;

    private Integer size;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private MessageEntity message;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private ChannelEntity channel;
}
