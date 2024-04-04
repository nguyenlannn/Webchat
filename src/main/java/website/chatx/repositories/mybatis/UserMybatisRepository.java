package website.chatx.repositories.mybatis;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import website.chatx.dto.prt.user.GetListFriendToAddGroupPrt;
import website.chatx.dto.prt.user.GetOneUserToAddFriendPrt;
import website.chatx.dto.rss.user.ListFriendToAddGroupRss;
import website.chatx.dto.rss.user.OneUserToAddFriendRss;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMybatisRepository {

    private final SqlSession sqlSession;

    public List<OneUserToAddFriendRss> getOneUserToAddFriend(GetOneUserToAddFriendPrt prt) {
        return sqlSession.selectList("website.chatx.repositories.mybatis.UserMybatisRepository.getOneUserToAddFriend", prt);
    }

    public Long countListFriendToAddGroup(GetListFriendToAddGroupPrt prt) {
        return sqlSession.selectOne("website.chatx.repositories.mybatis.UserMybatisRepository.countListFriendToAddGroup", prt);
    }

    public List<ListFriendToAddGroupRss> getListFriendToAddGroup(GetListFriendToAddGroupPrt prt) {
        return sqlSession.selectList("website.chatx.repositories.mybatis.UserMybatisRepository.getListFriendToAddGroup", prt);
    }
}
