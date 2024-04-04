package website.chatx.repositories.mybatis;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import website.chatx.dto.prt.message.GetListMessagePrt;
import website.chatx.dto.rss.message.list.ListMessageRss;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageMybatisRepository {

    private final SqlSession sqlSession;

    public Long countListMessage(GetListMessagePrt prt) {
        return sqlSession.selectOne("website.chatx.repositories.mybatis.MessageMybatisRepository.countListMessage", prt);
    }

    public List<ListMessageRss> getListMessage(GetListMessagePrt prt) {
        return sqlSession.selectList("website.chatx.repositories.mybatis.MessageMybatisRepository.getListMessage", prt);
    }
}
