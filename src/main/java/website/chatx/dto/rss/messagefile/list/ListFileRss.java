package website.chatx.dto.rss.messagefile.list;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListFileRss {
    private String id;
    private String name;
    private String url;
    private String contentType;
    private Integer size;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String senderId;
    private String senderEmail;
    private String senderName;
    private String senderAvatarUrl;
}
