package website.chatx.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import website.chatx.core.common.CommonResponse;
import website.chatx.dto.req.user.UpdateUserReq;
import website.chatx.service.UserService;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<CommonResponse> getUser() {
        return CommonResponse.success(userService.getUser());
    }

    @PatchMapping
    public ResponseEntity<CommonResponse> updateUser(@RequestBody @Valid UpdateUserReq req) {
        userService.updateUser(req);
        return CommonResponse.success("");
    }

    @PostMapping("/to-add-friend")
    public ResponseEntity<CommonResponse> getOneUserToAddFriend(@RequestBody List<String> email) {
        return CommonResponse.success(userService.getOneUserToAddFriend(email));
    }

    @GetMapping("/to-add-group")
    public ResponseEntity<CommonResponse> getListFriendToAddGroup(@RequestParam(defaultValue = "") String channelId,
                                                                  @RequestParam(defaultValue = "") String search,
                                                                  @RequestParam(required = false) Integer page,
                                                                  @RequestParam(required = false) Integer size) {
        return CommonResponse.success(userService.getListFriendToAddGroup(channelId, search, page, size));
    }
}
