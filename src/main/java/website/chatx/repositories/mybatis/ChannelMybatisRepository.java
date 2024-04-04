package website.chatx.repositories.mybatis;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import website.chatx.dto.prt.channel.GetDetailChannelPrt;
import website.chatx.dto.prt.channel.GetListChannelPrt;
import website.chatx.dto.rss.channel.DetailChannelRss;
import website.chatx.dto.rss.channel.ListChannelRss;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChannelMybatisRepository {

    private final SqlSession sqlSession;

    public Long countListChannel(GetListChannelPrt prt) {
        return sqlSession.selectOne("website.chatx.repositories.mybatis.ChannelMybatisRepository.countListChannel", prt);
    }

    public List<ListChannelRss> getListChannel(GetListChannelPrt prt) {
        return sqlSession.selectList("website.chatx.repositories.mybatis.ChannelMybatisRepository.getListChannel", prt);
    }

    public Long countListFriend(GetListChannelPrt prt) {
        return sqlSession.selectOne("website.chatx.repositories.mybatis.ChannelMybatisRepository.countListFriend", prt);
    }

    public List<ListChannelRss> getListFriend(GetListChannelPrt prt) {
        return sqlSession.selectList("website.chatx.repositories.mybatis.ChannelMybatisRepository.getListFriend", prt);
    }

    public Long countListGroup(GetListChannelPrt prt) {
        return sqlSession.selectOne("website.chatx.repositories.mybatis.ChannelMybatisRepository.countListGroup", prt);
    }

    public List<ListChannelRss> getListGroup(GetListChannelPrt prt) {
        return sqlSession.selectList("website.chatx.repositories.mybatis.ChannelMybatisRepository.getListGroup", prt);
    }

    public DetailChannelRss getDetailChannel(GetDetailChannelPrt prt) {
        return sqlSession.selectOne("website.chatx.repositories.mybatis.ChannelMybatisRepository.getDetailChannel", prt);
    }
}
