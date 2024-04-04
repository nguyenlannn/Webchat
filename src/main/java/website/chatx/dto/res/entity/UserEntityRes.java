package website.chatx.dto.res.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import website.chatx.core.base.BaseResponse;
import website.chatx.core.enums.UserGenderEnum;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserEntityRes extends BaseResponse {
    private String email;
    private String name;
    private UserGenderEnum gender;
    private String phone;
    private String address;
    private String avatarUrl;
    private Boolean isActivated;
    private List<UserActivationCodeEntityRes> userActivationCodes;
    private List<UserChannelEntityRes> userChannels;
    private List<MessageEntityRes> messages;
    private List<FileUpEntityRes> fileUps;
    private List<MessageFileEntityRes> messageFiles;
}
