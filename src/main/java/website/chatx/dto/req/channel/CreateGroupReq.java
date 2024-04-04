package website.chatx.dto.req.channel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateGroupReq {

    @NotBlank
    private String name;

    @NotBlank
    private String avatarUrl;

    @NotEmpty
    private List<String> userIds;
}
