package website.chatx.dto.prt.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetOneUserToAddFriendPrt {
    private String userId;
    private List<String> list;
}
