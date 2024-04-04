package website.chatx.dto.req.channel;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddUserGroupReq {

    @NotEmpty
    private List<String> userIds;
}
