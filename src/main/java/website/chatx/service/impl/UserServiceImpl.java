package website.chatx.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import website.chatx.core.common.CommonAuthContext;
import website.chatx.core.common.CommonListResponse;
import website.chatx.core.common.CommonPaginator;
import website.chatx.core.entities.UserEntity;
import website.chatx.core.exception.BusinessLogicException;
import website.chatx.core.mapper.UserMapper;
import website.chatx.core.utils.BeanCopyUtils;
import website.chatx.dto.prt.user.GetListFriendToAddGroupPrt;
import website.chatx.dto.prt.user.GetOneUserToAddFriendPrt;
import website.chatx.dto.req.user.UpdateUserReq;
import website.chatx.dto.res.entity.UserEntityRes;
import website.chatx.dto.res.user.ListFriendToAddGroupRes;
import website.chatx.dto.res.user.OneUserToAddFriendRes;
import website.chatx.dto.rss.user.OneUserToAddFriendRss;
import website.chatx.repositories.jpa.ChannelJpaRepository;
import website.chatx.repositories.jpa.UserJpaRepository;
import website.chatx.repositories.mybatis.UserMybatisRepository;
import website.chatx.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ChannelJpaRepository channelJpaRepository;
    private final UserJpaRepository userJpaRepository;

    private final UserMybatisRepository userMybatisRepository;

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    private final CommonAuthContext commonAuthContext;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final Log LOGGER = LogFactory.getLog(getClass());

    @Override
    @Transactional(readOnly = true)
    public List<OneUserToAddFriendRes> getOneUserToAddFriend(List<String> email) {
        List<OneUserToAddFriendRss> oneUserToAddFriendRss = userMybatisRepository.getOneUserToAddFriend(GetOneUserToAddFriendPrt.builder()
                .userId(commonAuthContext.getUserEntity().getId())
                .list(email)
                .build());
        if (oneUserToAddFriendRss == null) {
            throw new BusinessLogicException(-12);
        }
        return oneUserToAddFriendRss.stream().map(o -> {
            OneUserToAddFriendRes oneUserToAddFriendRes = new OneUserToAddFriendRes();
            BeanCopyUtils.copyProperties(oneUserToAddFriendRes, o);
            return oneUserToAddFriendRes;
        }).collect(Collectors.toList());
    }

    @Override
    public CommonListResponse<ListFriendToAddGroupRes> getListFriendToAddGroup(String channelId, String search, Integer page, Integer size) {
        if (StringUtils.hasText(channelId)) {
            channelJpaRepository.findByMyIdAndGroupId(
                    commonAuthContext.getUserEntity().getId(), channelId).orElseThrow(() -> new BusinessLogicException(-13));
        }
        Long countListFriend = userMybatisRepository.countListFriendToAddGroup(GetListFriendToAddGroupPrt.builder()
                .userId(commonAuthContext.getUserEntity().getId())
                .channelId(channelId)
                .search(search)
                .build());
        CommonPaginator commonPaginator = new CommonPaginator(page, size, countListFriend);
        if (countListFriend == 0) {
            return CommonListResponse.<ListFriendToAddGroupRes>builder()
                    .content(new ArrayList<>())
                    .page(commonPaginator.getPageNo())
                    .size(commonPaginator.getPageSize())
                    .totalPages(commonPaginator.getTotalPages())
                    .totalElements(commonPaginator.getTotalItems())
                    .build();
        }
        return CommonListResponse.<ListFriendToAddGroupRes>builder()
                .content(userMybatisRepository.getListFriendToAddGroup(GetListFriendToAddGroupPrt.builder()
                                .userId(commonAuthContext.getUserEntity().getId())
                                .channelId(channelId)
                                .search(search)
                                .offset(commonPaginator.getOffset())
                                .limit(commonPaginator.getLimit())
                                .build()).stream()
                        .map(o -> {
                                    ListFriendToAddGroupRes listUserToAddGroupRes = new ListFriendToAddGroupRes();
                                    BeanCopyUtils.copyProperties(listUserToAddGroupRes, o);
                                    return listUserToAddGroupRes;
                                }
                        )
                        .collect(Collectors.toList())
                )
                .page(commonPaginator.getPageNo())
                .size(commonPaginator.getPageSize())
                .totalPages(commonPaginator.getTotalPages())
                .totalElements(commonPaginator.getTotalItems())
                .build();
    }

    @Override
    public UserEntityRes getUser() {
        return userMapper.toUserEntityRes(commonAuthContext.getUserEntity());
    }

    @Override
    public void updateUser(UpdateUserReq req) {
        UserEntity userEntity = commonAuthContext.getUserEntity();

        if (StringUtils.hasText(req.getNewPassword())) {
            try {
                daoAuthenticationProvider.authenticate(
                        new UsernamePasswordAuthenticationToken(userEntity.getEmail(), req.getOldPassword()));
            } catch (BadCredentialsException e) {
                throw new BusinessLogicException(-37);
            }
            userEntity.setPassword(passwordEncoder.encode(req.getNewPassword()));
        }
        if (StringUtils.hasText(req.getName())) {
            userEntity.setName(req.getName());
        }
        if (StringUtils.hasText(req.getAvatarUrl())) {
            userEntity.setAvatarUrl(req.getAvatarUrl());
        }
        userJpaRepository.save(userEntity);
    }
}