package website.chatx.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website.chatx.core.common.CommonAuthContext;
import website.chatx.core.common.CommonListResponse;
import website.chatx.core.common.CommonPaginator;
import website.chatx.dto.prt.message.GetListMessagePrt;
import website.chatx.dto.res.message.list.FileRes;
import website.chatx.dto.res.message.list.ListMessageRes;
import website.chatx.dto.res.message.list.SenderRes;
import website.chatx.repositories.mybatis.MessageMybatisRepository;
import website.chatx.service.MessageService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageMybatisRepository messageMybatisRepository;

    private final CommonAuthContext commonAuthContext;

    @Override
    public CommonListResponse<ListMessageRes> getListMessage(String channelId, String content, Integer page, Integer size) {
        Long countListMessage = messageMybatisRepository.countListMessage(GetListMessagePrt.builder()
                .userId(commonAuthContext.getUserEntity().getId())
                .channelId(channelId)
                .content(content)
                .build());
        CommonPaginator commonPaginator = new CommonPaginator(page, size, countListMessage);
        if (countListMessage == 0) {
            return CommonListResponse.<ListMessageRes>builder()
                    .content(new ArrayList<>())
                    .page(commonPaginator.getPageNo())
                    .size(commonPaginator.getPageSize())
                    .totalPages(commonPaginator.getTotalPages())
                    .totalElements(commonPaginator.getTotalItems())
                    .build();
        }
        return CommonListResponse.<ListMessageRes>builder()
                .content(messageMybatisRepository.getListMessage(GetListMessagePrt.builder()
                                .userId(commonAuthContext.getUserEntity().getId())
                                .channelId(channelId)
                                .content(content)
                                .offset(commonPaginator.getOffset())
                                .limit(commonPaginator.getLimit())
                                .build()).stream()
                        .map(o -> ListMessageRes.builder()
                                .id(o.getId())
                                .content(o.getContent())
                                .createdAt(Timestamp.valueOf(o.getCreatedAt()).getTime())
                                .updatedAt(Timestamp.valueOf(o.getUpdatedAt()).getTime())
                                .sender(SenderRes.builder()
                                        .id(o.getSenderId())
                                        .email(o.getSenderEmail())
                                        .name(o.getSenderName())
                                        .avatarUrl(o.getSenderAvatarUrl())
                                        .build())
                                .files(o.getFiles().stream()
                                        .map(oo -> FileRes.builder()
                                                .id(oo.getFileId())
                                                .name(oo.getFileName())
                                                .url(oo.getFileUrl())
                                                .contentType(oo.getFileContentType())
                                                .size(oo.getFileSize())
                                                .createdAt(Timestamp.valueOf(oo.getFileCreatedAt()).getTime())
                                                .updatedAt(Timestamp.valueOf(oo.getFileUpdatedAt()).getTime())
                                                .build())
                                        .collect(Collectors.toList()))
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