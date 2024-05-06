package com.springBoot.item.repository;

import com.springBoot.item.dto.LoginDTO;
import com.springBoot.item.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor //생성자 주입
public class LoginRespository {
    private final SqlSessionTemplate sql;

    //로그인
    public MemberDTO login(LoginDTO loginDTO) {
        return sql.selectOne("loginSql.login", loginDTO);
    }
}
