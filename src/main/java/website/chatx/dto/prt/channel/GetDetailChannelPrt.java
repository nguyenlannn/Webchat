package website.chatx.dto.prt.channel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import website.chatx.core.enums.ChannelTypeEnum;

@Getter
@Setter
@Builder
public class GetDetailChannelPrt {
    private String channelId;
    private String userId;
}
