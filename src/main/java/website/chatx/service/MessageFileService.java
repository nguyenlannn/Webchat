package website.chatx.service;

import website.chatx.core.common.CommonListResponse;
import website.chatx.dto.res.messagefile.list.ListFileRes;

public interface MessageFileService {

    CommonListResponse<ListFileRes> getListFile(String channelId, String name, Integer page, Integer size);
}
