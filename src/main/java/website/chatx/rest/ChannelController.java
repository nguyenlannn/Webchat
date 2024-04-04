package website.chatx.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import website.chatx.async.PushMessageAsync;
import website.chatx.core.common.CommonAuthContext;
import website.chatx.core.common.CommonResponse;
import website.chatx.core.enums.ChannelTypeEnum;
import website.chatx.core.enums.UserChannelStatusEnum;
import website.chatx.dto.req.channel.*;
import website.chatx.dto.req.channel.createmessage.CreateMessageReq;
import website.chatx.service.ChannelService;
import website.chatx.service.MessageFileService;
import website.chatx.service.MessageService;
import website.chatx.service.UserChannelService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/channels")
public class ChannelController {

    private final ChannelService channelService;
    private final MessageService messageService;
    private final MessageFileService messageFileService;
    private final UserChannelService userChannelService;
    private final PushMessageAsync pushMessageAsync;
    private final CommonAuthContext commonAuthContext;

    @GetMapping
    public ResponseEntity<CommonResponse> getListChannel(@RequestParam(required = false) ChannelTypeEnum type,
                                                         @RequestParam(defaultValue = "") String search,
                                                         @RequestParam(required = false) UserChannelStatusEnum status,
                                                         @RequestParam(required = false) Integer page,
                                                         @RequestParam(required = false) Integer size) {
        return CommonResponse.success(channelService.getListChannel(type, search, status, page, size));
    }

    @GetMapping("/{channelId}")
    public ResponseEntity<CommonResponse> getDetailChannel(@PathVariable String channelId) {
        return CommonResponse.success(channelService.getDetailChannel(channelId));
    }

    @GetMapping("/{channelId}/messages")
    public ResponseEntity<CommonResponse> getListMessage(@PathVariable String channelId,
                                                         @RequestParam(defaultValue = "") String content,
                                                         @RequestParam(required = false) Integer page,
                                                         @RequestParam(required = false) Integer size) {
        return CommonResponse.success(messageService.getListMessage(channelId, content, page, size));
    }

    @GetMapping("/{channelId}/files")
    public ResponseEntity<CommonResponse> getListFile(@PathVariable String channelId,
                                                      @RequestParam(defaultValue = "") String name,
                                                      @RequestParam(required = false) Integer page,
                                                      @RequestParam(required = false) Integer size) {
        return CommonResponse.success(messageFileService.getListFile(channelId, name, page, size));
    }

    @GetMapping("/{channelId}/member")
    public ResponseEntity<CommonResponse> getListMember(@PathVariable String channelId,
                                                        @RequestParam(defaultValue = "") String search,
                                                        @RequestParam(required = false) UserChannelStatusEnum status,
                                                        @RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer size) {
        return CommonResponse.success(userChannelService.getListMember(channelId, search, status, page, size));
    }

    @PostMapping("/add-user-friend")
    public ResponseEntity<CommonResponse> addUserFriend(@RequestBody @Valid AddUserFriendReq req) {
        userChannelService.addUserFriend(req);
        return CommonResponse.success("");
    }

    @PostMapping("/{channelId}/react-user-friend")
    public ResponseEntity<CommonResponse> reactUserFriend(@PathVariable String channelId,
                                                          @RequestBody @Valid ReactUserFriendReq req) {
        userChannelService.reactUserFriend(channelId, req);
        return CommonResponse.success("");
    }

    @PostMapping("/{channelId}/add-user-group")
    public ResponseEntity<CommonResponse> addUserGroup(@PathVariable String channelId,
                                                       @RequestBody @Valid AddUserGroupReq req) {
        userChannelService.addUserGroup(channelId, req);
        return CommonResponse.success("");
    }

    @PostMapping("/{channelId}/react-user-group")
    public ResponseEntity<CommonResponse> reactUserGroup(@PathVariable String channelId,
                                                         @RequestBody @Valid ReactUserGroupReq req) {
        userChannelService.reactUserGroup(channelId, req);
        return CommonResponse.success("");
    }

    @PostMapping("/{channelId}/change-owner-group")
    public ResponseEntity<CommonResponse> changeOwnerGroup(@PathVariable String channelId,
                                                           @RequestBody @Valid ChangeOwnerGroupReq req) {
        userChannelService.changeOwnerGroup(channelId, req);
        return CommonResponse.success("");
    }

    @PostMapping("/create-group")
    public ResponseEntity<CommonResponse> createGroup(@RequestBody @Valid CreateGroupReq req) {
        userChannelService.createGroup(req);
        return CommonResponse.success("");
    }

    @PostMapping("/{channelId}/messages")
    public ResponseEntity<CommonResponse> createMessage(@PathVariable String channelId,
                                                        @RequestBody @Valid CreateMessageReq req) {
        String messageId = userChannelService.createMessage(channelId, req);
        pushMessageAsync.pushNotify(messageId, channelId, "CREATE", commonAuthContext.getUserEntity().getId());
        return CommonResponse.success("");
    }

    @PatchMapping("/{channelId}/messages/{messageId}")
    public ResponseEntity<CommonResponse> updateMessage(@PathVariable String channelId,
                                                        @PathVariable String messageId,
                                                        @RequestBody @Valid UpdateMessageReq req) {
        userChannelService.updateMessage(channelId, messageId, req);
        pushMessageAsync.pushNotify(messageId, channelId, "UPDATE", commonAuthContext.getUserEntity().getId());
        return CommonResponse.success("");
    }

    @DeleteMapping("/{channelId}/messages/{messageId}")
    public ResponseEntity<CommonResponse> deleteMessage(@PathVariable String channelId,
                                                        @PathVariable String messageId) {
        userChannelService.deleteMessage(channelId, messageId);
        pushMessageAsync.pushNotify(messageId, channelId, "DELETE", commonAuthContext.getUserEntity().getId());
        return CommonResponse.success("");
    }
}
