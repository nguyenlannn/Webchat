package website.chatx.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import website.chatx.core.base.BaseEntity;
import website.chatx.core.enums.UserGenderEnum;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {

    private String email;

    @Column(columnDefinition = "text")
    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private UserGenderEnum gender;

    private String phone;

    private String address;

    private String avatarUrl;

    private Boolean isActivated;

    @OneToMany(mappedBy = "user")
    private List<UserActivationCodeEntity> userActivationCodes;

    @OneToMany(mappedBy = "user")
    private List<UserChannelEntity> userChannels;

    @OneToMany(mappedBy = "sender")
    private List<MessageEntity> messages;

    @OneToMany(mappedBy = "user")
    private List<FileUpEntity> fileUps;

    @OneToMany(mappedBy = "sender")
    private List<MessageFileEntity> messageFiles;
}
