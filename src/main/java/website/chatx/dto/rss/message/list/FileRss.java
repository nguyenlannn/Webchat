package website.chatx.dto.rss.message.list;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileRss {
    private String fileId;
    private String fileName;
    private String fileUrl;
    private String fileContentType;
    private Integer fileSize;
    private LocalDateTime fileCreatedAt;
    private LocalDateTime fileUpdatedAt;
}
