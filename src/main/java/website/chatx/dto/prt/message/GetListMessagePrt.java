package website.chatx.dto.prt.message;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetListMessagePrt {
    private String userId;
    private String channelId;
    private String content;
    private int offset;
    private int limit;
}
