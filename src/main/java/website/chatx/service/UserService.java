package website.chatx.service;

import website.chatx.core.common.CommonListResponse;
import website.chatx.dto.req.user.UpdateUserReq;
import website.chatx.dto.res.entity.UserEntityRes;
import website.chatx.dto.res.user.OneUserToAddFriendRes;
import website.chatx.dto.res.user.ListFriendToAddGroupRes;

import java.util.List;

public interface UserService {
    List<OneUserToAddFriendRes> getOneUserToAddFriend(List<String> email);

    CommonListResponse<ListFriendToAddGroupRes> getListFriendToAddGroup(String channelId, String search, Integer page, Integer size);

    UserEntityRes getUser();

    void updateUser(UpdateUserReq req);
}
