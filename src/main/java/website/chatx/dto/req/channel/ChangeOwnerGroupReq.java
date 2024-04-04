package website.chatx.dto.req.channel;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeOwnerGroupReq {

    @NotNull
    private String userId;
}
