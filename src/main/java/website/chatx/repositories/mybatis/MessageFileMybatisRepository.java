package website.chatx.repositories.mybatis;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import website.chatx.dto.prt.messagefile.GetListFilePrt;
import website.chatx.dto.rss.messagefile.list.ListFileRss;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageFileMybatisRepository {

    private final SqlSession sqlSession;

    public Long countListFile(GetListFilePrt prt) {
        return sqlSession.selectOne("website.chatx.repositories.mybatis.MessageFileMybatisRepository.countListFile", prt);
    }

    public List<ListFileRss> getListFile(GetListFilePrt prt) {
        return sqlSession.selectList("website.chatx.repositories.mybatis.MessageFileMybatisRepository.getListFile", prt);
    }
}
