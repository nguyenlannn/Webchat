package website.chatx.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import website.chatx.core.common.CommonAuthContext;
import website.chatx.core.common.CommonListResponse;
import website.chatx.core.common.CommonPaginator;
import website.chatx.core.entities.*;
import website.chatx.core.enums.ChannelTypeEnum;
import website.chatx.core.enums.UserChannelStatusEnum;
import website.chatx.core.exception.BusinessLogicException;
import website.chatx.core.utils.BeanCopyUtils;
import website.chatx.dto.prt.userchannel.GetListMemberPrt;
import website.chatx.dto.req.channel.*;
import website.chatx.dto.req.channel.createmessage.CreateMessageReq;
import website.chatx.dto.res.user.OneUserToAddFriendRes;
import website.chatx.dto.res.userchannel.list.ListMemberRes;
import website.chatx.repositories.jpa.*;
import website.chatx.repositories.mybatis.UserChannelMybatisRepository;
import website.chatx.service.UserChannelService;
import website.chatx.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserChannelServiceImpl implements UserChannelService {

    private final UserChannelJpaRepository userChannelJpaRepository;
    private final ChannelJpaRepository channelJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final MessageJpaRepository messageJpaRepository;
    private final MessageFileJpaRepository messageFileJpaRepository;

    private final UserChannelMybatisRepository userChannelMybatisRepository;
    private final UserService userService;

    private final CommonAuthContext commonAuthContext;

    private final Log LOGGER = LogFactory.getLog(getClass());

    @Override
    @Transactional(readOnly = true)
    public CommonListResponse<ListMemberRes> getListMember(String channelId, String search, UserChannelStatusEnum status, Integer page, Integer size) {
        Long countListMember = userChannelMybatisRepository.countListMember(GetListMemberPrt.builder()
                .userId(commonAuthContext.getUserEntity().getId())
                .channelId(channelId)
                .search(search)
                .status(status)
                .build());
        CommonPaginator commonPaginator = new CommonPaginator(page, size, countListMember);
        if (countListMember == 0) {
            return CommonListResponse.<ListMemberRes>builder()
                    .content(new ArrayList<>())
                    .page(commonPaginator.getPageNo())
                    .size(commonPaginator.getPageSize())
                    .totalPages(commonPaginator.getTotalPages())
                    .totalElements(commonPaginator.getTotalItems())
                    .build();
        }

        List<ListMemberRes> listMemberRes = userChannelMybatisRepository.getListMember(GetListMemberPrt.builder()
                        .userId(commonAuthContext.getUserEntity().getId())
                        .channelId(channelId)
                        .search(search)
                        .status(status)
                        .offset(commonPaginator.getOffset())
                        .limit(commonPaginator.getLimit())
                        .build()).stream()
                .map(o -> {
                    ListMemberRes listMember = new ListMemberRes();
                    BeanCopyUtils.copyProperties(listMember, o);
                    return listMember;
                })
                .collect(Collectors.toList());


        List<OneUserToAddFriendRes> userToAddFriendRes = userService.getOneUserToAddFriend(listMemberRes.stream().map(ListMemberRes::getEmail).collect(Collectors.toList()));

        listMemberRes.forEach(o -> {
            final boolean[] check = {false};
            userToAddFriendRes.forEach(oo -> {
                if (oo.getId().equals(o.getId())) {
                    check[0] = true;
                    o.setFriendStatus(oo.getStatus());
                }
            });
            if (!check[0]) {
                o.setFriendStatus(null);
            }
        });

        return CommonListResponse.<ListMemberRes>builder()
                .content(listMemberRes)
                .page(commonPaginator.getPageNo())
                .size(commonPaginator.getPageSize())
                .totalPages(commonPaginator.getTotalPages())
                .totalElements(commonPaginator.getTotalItems())
                .build();
    }

    @Override
    public void addUserFriend(AddUserFriendReq req) {
        UserEntity userEntity = userJpaRepository.findByIdAndActivatedTrue(req.getUserId())
                .orElseThrow(() -> new BusinessLogicException(-14));

        List<UserChannelEntity> userChannelEntities = userChannelJpaRepository.findByMyIdAndTheirId(
                commonAuthContext.getUserEntity().getId(),
                req.getUserId());
        if (CollectionUtils.isEmpty(userChannelEntities)) {
            ChannelEntity channelEntity = channelJpaRepository.save(ChannelEntity.builder()
                    .type(ChannelTypeEnum.FRIEND)
                    .build());
            UserChannelEntity userChannelEntity1 = UserChannelEntity.builder()
                    .status(UserChannelStatusEnum.ACCEPT)
                    .user(commonAuthContext.getUserEntity())
                    .channel(channelEntity)
                    .build();
            UserChannelEntity userChannelEntity2 = UserChannelEntity.builder()
                    .status(UserChannelStatusEnum.NEW)
                    .user(userEntity)
                    .channel(channelEntity)
                    .build();
            userChannelJpaRepository.saveAll(Arrays.asList(userChannelEntity1, userChannelEntity2));
            return;
        }
        userChannelEntities.forEach(o -> {
            if (o.getUser().getId().equals(commonAuthContext.getUserEntity().getId())
                    && (o.getStatus() == UserChannelStatusEnum.NEW || o.getStatus() == UserChannelStatusEnum.REJECT)) {
                o.setStatus(UserChannelStatusEnum.ACCEPT);
            } else {
                if (o.getStatus() == UserChannelStatusEnum.REJECT) {
                    o.setStatus(UserChannelStatusEnum.NEW);
                }
            }
        });
        userChannelJpaRepository.saveAll(userChannelEntities);
    }

    @Override
    public void reactUserFriend(String channelId, ReactUserFriendReq req) {
        ChannelEntity channelEntity = channelJpaRepository.findById(channelId)
                .orElseThrow(() -> new BusinessLogicException(-18));

        if (channelEntity.getType() == ChannelTypeEnum.GROUP) {
            throw new BusinessLogicException(-19);
        }

        List<UserChannelEntity> userChannelEntities = channelEntity.getUserChannels();
        boolean exists = false;
        for (UserChannelEntity o : userChannelEntities) {
            if (o.getUser().getId().equals(commonAuthContext.getUserEntity().getId())) {
                o.setStatus(req.getStatus());
                exists = true;
            }
        }
        if (!exists) {
            throw new BusinessLogicException(-20);
        }
        userChannelJpaRepository.saveAll(userChannelEntities);
    }

    @Override
    public void addUserGroup(String channelId, AddUserGroupReq req) {
        List<UserEntity> userEntities = userJpaRepository.findByListIdAndActivatedTrue(req.getUserIds());
        if (userEntities.size() != req.getUserIds().size()) {
            throw new BusinessLogicException(-15);
        }

        for (UserEntity o : userEntities) {
            if (channelJpaRepository.checkFriend(commonAuthContext.getUserEntity().getId(), o.getId()).orElse(null) == null) {
                throw new BusinessLogicException(-16);
            }
        }

        ChannelEntity channelEntity = channelJpaRepository.findByMyIdAndGroupId(commonAuthContext.getUserEntity().getId(), channelId)
                .orElseThrow(() -> new BusinessLogicException(-17));

        List<UserChannelEntity> userChannelEntities = channelEntity.getUserChannels();

        userEntities.forEach(i -> {
            boolean exist = false;
            for (UserChannelEntity o : userChannelEntities) {
                if (o.getUser().getId().equals(i.getId())) {
                    if (o.getStatus() == UserChannelStatusEnum.REJECT) {
                        o.setStatus(channelEntity.getOwnerId().equals(commonAuthContext.getUserEntity().getId())
                                ? UserChannelStatusEnum.ACCEPT
                                : UserChannelStatusEnum.NEW
                        );
                    }
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                userChannelEntities.add(UserChannelEntity.builder()
                        .status(channelEntity.getOwnerId().equals(commonAuthContext.getUserEntity().getId())
                                ? UserChannelStatusEnum.ACCEPT
                                : UserChannelStatusEnum.NEW
                        )
                        .user(i)
                        .channel(channelEntity)
                        .build());
            }
        });
        userChannelJpaRepository.saveAll(userChannelEntities);
    }

    @Override
    public void reactUserGroup(String channelId, ReactUserGroupReq req) {
        ChannelEntity channelEntity = channelJpaRepository.findById(channelId)
                .orElseThrow(() -> new BusinessLogicException(-21));

        if (channelEntity.getType() == ChannelTypeEnum.FRIEND) {
            throw new BusinessLogicException(-22);
        }

        if (req.getUserId().equals(commonAuthContext.getUserEntity().getId()) && req.getStatus() == UserChannelStatusEnum.REJECT) {

        } else {
            if (!channelEntity.getOwnerId().equals(commonAuthContext.getUserEntity().getId())) {
                throw new BusinessLogicException(-23);
            }
        }

        List<UserChannelEntity> userChannelEntities = channelEntity.getUserChannels();
        boolean exists = false;
        for (UserChannelEntity o : userChannelEntities) {
            if (o.getUser().getId().equals(req.getUserId())) {
                o.setStatus(req.getStatus());
                exists = true;
            }
        }
        if (!exists) {
            throw new BusinessLogicException(-24);
        }
        userChannelJpaRepository.saveAll(userChannelEntities);
    }

    @Override
    public void changeOwnerGroup(String channelId, ChangeOwnerGroupReq req) {
        ChannelEntity channelEntity = channelJpaRepository.findById(channelId)
                .orElseThrow(() -> new BusinessLogicException(-21));

        if (channelEntity.getType() == ChannelTypeEnum.FRIEND) {
            throw new BusinessLogicException(-22);
        }

        if (!channelEntity.getOwnerId().equals(commonAuthContext.getUserEntity().getId())) {
            throw new BusinessLogicException(-23);
        }

        List<UserChannelEntity> userChannelEntities = channelEntity.getUserChannels();
        boolean exists = false;
        for (UserChannelEntity o : userChannelEntities) {
            if (o.getUser().getId().equals(req.getUserId())) {
                channelEntity.setOwnerId(req.getUserId());
                exists = true;
            }
        }
        if (!exists) {
            throw new BusinessLogicException(-24);
        }
        channelJpaRepository.save(channelEntity);
    }

    @Override
    public void createGroup(CreateGroupReq req) {
        if (req.getUserIds().size() < 2) {
            throw new BusinessLogicException(-25);
        }

        List<UserEntity> userEntities = userJpaRepository.findByListIdAndActivatedTrue(req.getUserIds());
        if (userEntities.size() != req.getUserIds().size()) {
            throw new BusinessLogicException(-26);
        }

        for (UserEntity o : userEntities) {
            if (channelJpaRepository.checkFriend(commonAuthContext.getUserEntity().getId(), o.getId()).orElse(null) == null) {
                throw new BusinessLogicException(-27);
            }
        }

        ChannelEntity channelEntity = channelJpaRepository.save(ChannelEntity.builder()
                .ownerId(commonAuthContext.getUserEntity().getId())
                .name(req.getName())
                .avatarUrl(req.getAvatarUrl())
                .type(ChannelTypeEnum.GROUP)
                .build());

        List<UserChannelEntity> userChannelEntities = new ArrayList<>();
        userChannelEntities.add(UserChannelEntity.builder()
                .status(UserChannelStatusEnum.ACCEPT)
                .user(commonAuthContext.getUserEntity())
                .channel(channelEntity)
                .build());
        for (UserEntity o : userEntities) {
            userChannelEntities.add(UserChannelEntity.builder()
                    .status(UserChannelStatusEnum.ACCEPT)
                    .user(o)
                    .channel(channelEntity)
                    .build());
        }
        userChannelJpaRepository.saveAll(userChannelEntities);
    }

    @Override
    public String createMessage(String channelId, CreateMessageReq req) {
        ChannelEntity channelEntity = channelJpaRepository.findByMyIdAndChannel1Id(commonAuthContext.getUserEntity().getId(), channelId)
                .orElseThrow(() -> new BusinessLogicException(-28));

        MessageEntity messageEntity = messageJpaRepository.save(MessageEntity.builder()
                .content(req.getContent())
                .sender(commonAuthContext.getUserEntity())
                .channel(channelEntity)
                .build());

        if (!CollectionUtils.isEmpty(req.getFiles())) {
            List<MessageFileEntity> messageFileEntities = new ArrayList<>();
            req.getFiles().forEach(o -> messageFileEntities.add(MessageFileEntity.builder()
                    .name(o.getName())
                    .url(o.getUrl())
                    .contentType(o.getContentType())
                    .size(o.getSize())
                    .sender(commonAuthContext.getUserEntity())
                    .message(messageEntity)
                    .channel(channelEntity)
                    .build()));
            messageFileJpaRepository.saveAll(messageFileEntities);
        }
        return messageEntity.getId();
    }

    @Override
    public void updateMessage(String channelId, String messageId, UpdateMessageReq req) {
        ChannelEntity channelEntity = channelJpaRepository.findByMyIdAndChannel1Id(commonAuthContext.getUserEntity().getId(), channelId)
                .orElseThrow(() -> new BusinessLogicException(-29));

        MessageEntity messageEntity = messageJpaRepository.findById(messageId)
                .orElseThrow(() -> new BusinessLogicException(-30));

        if (!messageEntity.getChannel().getId().equals(channelEntity.getId())) {
            throw new BusinessLogicException(-31);
        }

        if (!messageEntity.getSender().getId().equals(commonAuthContext.getUserEntity().getId())) {
            throw new BusinessLogicException(-32);
        }

        messageEntity.setContent(req.getContent());
        messageJpaRepository.save(messageEntity);
    }

    @Override
    public void deleteMessage(String channelId, String messageId) {
        ChannelEntity channelEntity = channelJpaRepository.findByMyIdAndChannel1Id(commonAuthContext.getUserEntity().getId(), channelId)
                .orElseThrow(() -> new BusinessLogicException(-33));

        MessageEntity messageEntity = messageJpaRepository.findById(messageId)
                .orElseThrow(() -> new BusinessLogicException(-34));

        if (!messageEntity.getChannel().getId().equals(channelEntity.getId())) {
            throw new BusinessLogicException(-35);
        }

        if (!messageEntity.getSender().getId().equals(commonAuthContext.getUserEntity().getId())) {
            throw new BusinessLogicException(-36);
        }

        messageFileJpaRepository.deleteAllById(messageEntity.getMessageFiles().stream().map(o->o.getId()).collect(Collectors.toList()));
        messageJpaRepository.delete(messageEntity);
    }
}
