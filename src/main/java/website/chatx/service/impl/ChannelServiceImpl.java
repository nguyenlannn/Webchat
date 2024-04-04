package website.chatx.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website.chatx.core.common.CommonAuthContext;
import website.chatx.core.common.CommonListResponse;
import website.chatx.core.common.CommonPaginator;
import website.chatx.core.enums.ChannelTypeEnum;
import website.chatx.core.enums.UserChannelStatusEnum;
import website.chatx.core.exception.BusinessLogicException;
import website.chatx.dto.prt.channel.GetDetailChannelPrt;
import website.chatx.dto.prt.channel.GetListChannelPrt;
import website.chatx.dto.res.channel.DetailChannelRes;
import website.chatx.dto.res.channel.list.CurrentMessageRes;
import website.chatx.dto.res.channel.list.ListChannelRes;
import website.chatx.dto.res.channel.list.SenderRes;
import website.chatx.dto.rss.channel.DetailChannelRss;
import website.chatx.dto.rss.channel.ListChannelRss;
import website.chatx.repositories.mybatis.ChannelMybatisRepository;
import website.chatx.service.ChannelService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelMybatisRepository channelMybatisRepository;

    private final CommonAuthContext commonAuthContext;

    @Override
    @Transactional(readOnly = true)
    public CommonListResponse<ListChannelRes> getListChannel(ChannelTypeEnum type, String search, UserChannelStatusEnum status, Integer page, Integer size) {
        Long countListChannel;
        if (type == null) {
            countListChannel = channelMybatisRepository.countListChannel(GetListChannelPrt.builder()
                    .userId(commonAuthContext.getUserEntity().getId())
                    .status(status)
                    .build());
        } else if (type == ChannelTypeEnum.FRIEND) {
            countListChannel = channelMybatisRepository.countListFriend(GetListChannelPrt.builder()
                    .userId(commonAuthContext.getUserEntity().getId())
                    .search(search)
                    .status(status)
                    .build());
        } else {
            countListChannel = channelMybatisRepository.countListGroup(GetListChannelPrt.builder()
                    .userId(commonAuthContext.getUserEntity().getId())
                    .search(search)
                    .status(status)
                    .build());
        }
        CommonPaginator commonPaginator = new CommonPaginator(page, size, countListChannel);
        if (countListChannel == 0) {
            return CommonListResponse.<ListChannelRes>builder()
                    .content(new ArrayList<>())
                    .page(commonPaginator.getPageNo())
                    .size(commonPaginator.getPageSize())
                    .totalPages(commonPaginator.getTotalPages())
                    .totalElements(commonPaginator.getTotalItems())
                    .build();
        }
        List<ListChannelRss> listChannelRss;
        if (type == null) {
            listChannelRss = channelMybatisRepository.getListChannel(GetListChannelPrt.builder()
                    .userId(commonAuthContext.getUserEntity().getId())
                    .search(search)
                    .status(status)
                    .offset(commonPaginator.getOffset())
                    .limit(commonPaginator.getLimit())
                    .build());
        } else if (type == ChannelTypeEnum.FRIEND) {
            listChannelRss = channelMybatisRepository.getListFriend(GetListChannelPrt.builder()
                    .userId(commonAuthContext.getUserEntity().getId())
                    .search(search)
                    .status(status)
                    .offset(commonPaginator.getOffset())
                    .limit(commonPaginator.getLimit())
                    .build());
        } else {
            listChannelRss = channelMybatisRepository.getListGroup(GetListChannelPrt.builder()
                    .userId(commonAuthContext.getUserEntity().getId())
                    .search(search)
                    .status(status)
                    .offset(commonPaginator.getOffset())
                    .limit(commonPaginator.getLimit())
                    .build());
        }
        return CommonListResponse.<ListChannelRes>builder()
                .content(listChannelRss.stream()
                        .map(o -> ListChannelRes.builder()
                                .id(o.getId())
                                .friendId(o.getFriendId())
                                .friendEmail(o.getType() == ChannelTypeEnum.FRIEND ? o.getFriendEmail() : null)
                                .name(o.getType() == ChannelTypeEnum.FRIEND ? o.getFriendName() : o.getName())
                                .avatarUrl(o.getType() == ChannelTypeEnum.FRIEND ? o.getFriendAvatarUrl() : o.getAvatarUrl())
                                .type(o.getType())
                                .status(o.getStatus())
                                .friendStatus(o.getFriendStatus())
                                .createdAt(Timestamp.valueOf(o.getCreatedAt()).getTime())
                                .updatedAt(Timestamp.valueOf(o.getUpdatedAt()).getTime())
                                .currentMessage(o.getCurrentMessageId() != null
                                        ? CurrentMessageRes.builder()
                                        .id(o.getCurrentMessageId())
                                        .content(o.getCurrentMessageContent())
                                        .createdAt(Timestamp.valueOf(o.getCurrentMessageCreatedAt()).getTime())
                                        .updatedAt(Timestamp.valueOf(o.getCurrentMessageUpdatedAt()).getTime())
                                        .sender(SenderRes.builder()
                                                .id(o.getSenderCurrentMessageId())
                                                .email(o.getSenderCurrentMessageEmail())
                                                .name(o.getSenderCurrentMessageName())
                                                .avatarUrl(o.getSenderCurrentMessageAvatarUrl())
                                                .build())
                                        .build()
                                        : null
                                )
                                .build())
                        .collect(Collectors.toList()))
                .page(commonPaginator.getPageNo())
                .size(commonPaginator.getPageSize())
                .totalPages(commonPaginator.getTotalPages())
                .totalElements(commonPaginator.getTotalItems())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public DetailChannelRes getDetailChannel(String channelId) {
        DetailChannelRss detailChannelRss = channelMybatisRepository.getDetailChannel(GetDetailChannelPrt.builder()
                .channelId(channelId)
                .userId(commonAuthContext.getUserEntity().getId())
                .build());
        if (detailChannelRss == null) {
            throw new BusinessLogicException(-11);
        }
        return DetailChannelRes.builder()
                .id(detailChannelRss.getId())
                .ownerId(detailChannelRss.getOwnerId())
                .name(detailChannelRss.getType() == ChannelTypeEnum.FRIEND ? detailChannelRss.getFriendName() : detailChannelRss.getName())
                .email(detailChannelRss.getType() == ChannelTypeEnum.FRIEND ? detailChannelRss.getFriendEmail() : null)
                .avatarUrl(detailChannelRss.getType() == ChannelTypeEnum.FRIEND ? detailChannelRss.getFriendAvatarUrl() : detailChannelRss.getAvatarUrl())
                .type(detailChannelRss.getType())
                .createdAt(Timestamp.valueOf(detailChannelRss.getCreatedAt()).getTime())
                .updatedAt(Timestamp.valueOf(detailChannelRss.getUpdatedAt()).getTime())
                .status(detailChannelRss.getStatus())
                .friendStatus(detailChannelRss.getType() == ChannelTypeEnum.FRIEND ? detailChannelRss.getFriendStatus() : null)
                .build();
    }
}
