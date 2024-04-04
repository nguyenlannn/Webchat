package website.chatx.repositories.mybatis;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import website.chatx.dto.prt.userchannel.GetListMemberPrt;
import website.chatx.dto.rss.userchannel.list.ListMemberRss;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserChannelMybatisRepository {

    private final SqlSession sqlSession;

    public Long countListMember(GetListMemberPrt prt) {
        return sqlSession.selectOne("website.chatx.repositories.mybatis.UserChannelMybatisRepository.countListMember", prt);
    }

    public List<ListMemberRss> getListMember(GetListMemberPrt prt) {
        return sqlSession.selectList("website.chatx.repositories.mybatis.UserChannelMybatisRepository.getListMember", prt);
    }
}
