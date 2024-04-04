package website.chatx.dto.res.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import website.chatx.core.base.BaseResponse;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MessageEntityRes extends BaseResponse {

    private String receiverId;

    private String content;

    private List<MessageFileEntityRes> messageFiles;

    private UserEntityRes sender;

    private ChannelEntityRes channel;
}
