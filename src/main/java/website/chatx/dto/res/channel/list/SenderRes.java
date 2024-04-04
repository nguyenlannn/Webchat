package website.chatx.dto.res.channel.list;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SenderRes {
    private String id;
    private String email;
    private String name;
    private String avatarUrl;
}
