package website.chatx.service;

import website.chatx.core.common.CommonListResponse;
import website.chatx.core.enums.UserChannelStatusEnum;
import website.chatx.dto.req.channel.*;
import website.chatx.dto.req.channel.createmessage.CreateMessageReq;
import website.chatx.dto.res.userchannel.list.ListMemberRes;

public interface UserChannelService {

    CommonListResponse<ListMemberRes> getListMember(String channelId, String search, UserChannelStatusEnum status, Integer page, Integer size);

    void addUserFriend(AddUserFriendReq req);

    void reactUserFriend(String channelId, ReactUserFriendReq req);

    void addUserGroup(String channelId, AddUserGroupReq req);

    void reactUserGroup(String channelId, ReactUserGroupReq req);

    void changeOwnerGroup(String channelId, ChangeOwnerGroupReq req);

    void createGroup(CreateGroupReq req);

    String createMessage(String channelId, CreateMessageReq req);

    void updateMessage(String channelId, String messageId, UpdateMessageReq req);

    void deleteMessage(String channelId, String messageId);
}
