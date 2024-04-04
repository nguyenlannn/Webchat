package website.chatx.service;

import website.chatx.core.common.CommonListResponse;
import website.chatx.dto.res.message.list.ListMessageRes;

public interface MessageService {
    CommonListResponse<ListMessageRes> getListMessage(String channelId, String content, Integer page, Integer size);
}
