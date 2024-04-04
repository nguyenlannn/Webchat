package website.chatx.dto.prt.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetListFriendToAddGroupPrt {
    private String userId;
    private String channelId;
    private String search;
    private int offset;
    private int limit;
}
