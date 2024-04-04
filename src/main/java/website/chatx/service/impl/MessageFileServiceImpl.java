package website.chatx.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website.chatx.core.common.CommonAuthContext;
import website.chatx.core.common.CommonListResponse;
import website.chatx.core.common.CommonPaginator;
import website.chatx.dto.prt.messagefile.GetListFilePrt;
import website.chatx.dto.res.messagefile.list.ListFileRes;
import website.chatx.dto.res.messagefile.list.SenderRes;
import website.chatx.repositories.mybatis.MessageFileMybatisRepository;
import website.chatx.service.MessageFileService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageFileServiceImpl implements MessageFileService {
    private final MessageFileMybatisRepository messageFileMybatisRepository;

    private final CommonAuthContext commonAuthContext;

    @Override
    public CommonListResponse<ListFileRes> getListFile(String channelId, String name, Integer page, Integer size) {
        Long countListFile = messageFileMybatisRepository.countListFile(GetListFilePrt.builder()
                .userId(commonAuthContext.getUserEntity().getId())
                .channelId(channelId)
                .name(name)
                .build());
        CommonPaginator commonPaginator = new CommonPaginator(page, size, countListFile);
        if (countListFile == 0) {
            return CommonListResponse.<ListFileRes>builder()
                    .content(new ArrayList<>())
                    .page(commonPaginator.getPageNo())
                    .size(commonPaginator.getPageSize())
                    .totalPages(commonPaginator.getTotalPages())
                    .totalElements(commonPaginator.getTotalItems())
                    .build();
        }
        return CommonListResponse.<ListFileRes>builder()
                .content(messageFileMybatisRepository.getListFile(GetListFilePrt.builder()
                                .userId(commonAuthContext.getUserEntity().getId())
                                .channelId(channelId)
                                .name(name)
                                .offset(commonPaginator.getOffset())
                                .limit(commonPaginator.getLimit())
                                .build()).stream()
                        .map(o -> ListFileRes.builder()
                                .id(o.getId())
                                .name(o.getName())
                                .url(o.getUrl())
                                .contentType(o.getContentType())
                                .size(o.getSize())
                                .createdAt(Timestamp.valueOf(o.getCreatedAt()).getTime())
                                .updatedAt(Timestamp.valueOf(o.getUpdatedAt()).getTime())
                                .sender(SenderRes.builder()
                                        .id(o.getSenderId())
                                        .email(o.getSenderEmail())
                                        .name(o.getSenderName())
                                        .avatarUrl(o.getSenderAvatarUrl())
                                        .build())
                                .build()
                        )
                        .collect(Collectors.toList())
                )
                .page(commonPaginator.getPageNo())
                .size(commonPaginator.getPageSize())
                .totalPages(commonPaginator.getTotalPages())
                .totalElements(commonPaginator.getTotalItems())
                .build();
    }
}