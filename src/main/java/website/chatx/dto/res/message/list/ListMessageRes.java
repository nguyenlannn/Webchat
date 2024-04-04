package website.chatx.dto.res.message.list;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListMessageRes {
    private String id;
    private String content;
    private Long createdAt;
    private Long updatedAt;

    private SenderRes sender;

    private List<FileRes> files;
}
