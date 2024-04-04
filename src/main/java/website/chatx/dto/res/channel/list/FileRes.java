package website.chatx.dto.res.channel.list;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileRes {
    private String id;
    private String name;
    private String url;
    private String contentType;
    private Integer size;
    private Long createdAt;
    private Long updatedAt;
}
