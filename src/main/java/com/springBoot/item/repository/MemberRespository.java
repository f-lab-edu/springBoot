package com.springBoot.item.repository;

import com.springBoot.item.dto.ItemDTO;
import com.springBoot.item.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor //생성자 주입
public class MemberRespository {
    private final SqlSessionTemplate sql;

    //회원가입
    public void saveMember(MemberDTO member) {
        sql.insert("memberSql.saveMember", member);
    }
}
