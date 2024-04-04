package website.chatx.dto.res.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import website.chatx.core.base.BaseResponse;
import website.chatx.core.enums.UserChannelStatusEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserChannelEntityRes extends BaseResponse {

    private UserChannelStatusEnum status;

    private UserEntityRes user;

    private ChannelEntityRes channel;
}
