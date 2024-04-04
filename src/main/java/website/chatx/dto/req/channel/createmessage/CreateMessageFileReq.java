package website.chatx.dto.req.channel.createmessage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMessageFileReq {
    private String url;
    private String name;
    private String contentType;
    private Integer size;
}
