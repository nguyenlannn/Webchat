package website.chatx.dto.prt.channel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import website.chatx.core.enums.ChannelTypeEnum;
import website.chatx.core.enums.UserChannelStatusEnum;

@Getter
@Setter
@Builder
public class GetListChannelPrt {
    private String userId;
    private ChannelTypeEnum type;
    private String search;
    UserChannelStatusEnum status;
    private int offset;
    private int limit;
}
