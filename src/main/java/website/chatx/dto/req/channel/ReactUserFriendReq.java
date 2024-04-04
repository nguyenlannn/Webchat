package website.chatx.dto.req.channel;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import website.chatx.core.enums.UserChannelStatusEnum;

@Getter
@Setter
public class ReactUserFriendReq {

    @NotNull
    private UserChannelStatusEnum status;
}
