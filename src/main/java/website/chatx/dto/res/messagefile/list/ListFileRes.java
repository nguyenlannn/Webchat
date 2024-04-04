package website.chatx.dto.res.messagefile.list;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListFileRes {
    private String id;
    private String name;
    private String url;
    private String contentType;
    private Integer size;
    private Long createdAt;
    private Long updatedAt;

    private SenderRes sender;
}
