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
public class FileUpEntityRes extends BaseResponse {

    private String url;

    private String name;

    private String contentType;

    private Long size;

    private UserEntityRes user;
}
