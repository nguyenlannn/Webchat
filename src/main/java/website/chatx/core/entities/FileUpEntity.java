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
@Table(name = "file_up")
public class FileUpEntity extends BaseEntity {

    private String url;

    private String name;

    private String contentType;

    private Long size;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
