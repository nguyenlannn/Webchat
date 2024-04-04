package website.chatx.dto.res.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import website.chatx.core.base.BaseResponse;
import website.chatx.core.enums.ChannelTypeEnum;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ChannelEntityRes extends BaseResponse {

    private String ownerId;

    private String name;

    private String avatarUrl;

    private ChannelTypeEnum type;

    private List<UserChannelEntityRes> userChannels;

    private List<MessageFileEntityRes> messageFiles;

    private List<MessageEntityRes> messages;
}
