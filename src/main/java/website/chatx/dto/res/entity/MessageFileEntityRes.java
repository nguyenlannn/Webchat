package website.chatx.dto.res.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import website.chatx.core.base.BaseResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MessageFileEntityRes extends BaseResponse {
    private String name;
    private String url;
    private String contentType;
    private Integer size;
    private UserEntityRes sender;
    private MessageEntityRes message;
    private ChannelEntityRes channel;
}
