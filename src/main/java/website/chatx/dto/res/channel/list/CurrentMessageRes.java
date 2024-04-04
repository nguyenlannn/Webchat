package website.chatx.dto.res.channel.list;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentMessageRes {
    private String id;
    private String type;
    private String content;
    private Long createdAt;
    private Long updatedAt;
    private List<FileRes> files;
    private SenderRes sender;
}
