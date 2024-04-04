package website.chatx.core.common;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import website.chatx.core.entities.UserEntity;

@Getter
@Setter
@Component
@Builder
@RequestScope
@NoArgsConstructor
@AllArgsConstructor
public class CommonAuthContext {
    private UserEntity userEntity;

    public void set(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
