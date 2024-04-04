package website.chatx.repositories.mybatis;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserActivationCodeMybatisRepository {

    private final SqlSession sqlSession;

}
