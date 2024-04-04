package website.chatx.dto.prt.userchannel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import website.chatx.core.enums.UserChannelStatusEnum;

@Getter
@Setter
@Builder
public class GetListMemberPrt {
    private String userId;
    private String channelId;
    private String search;
    private UserChannelStatusEnum status;
    private int offset;
    private int limit;
}
