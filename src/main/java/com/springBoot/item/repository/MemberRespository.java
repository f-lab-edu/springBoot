package com.springBoot.item.repository;

import com.springBoot.item.dto.ItemDTO;
import com.springBoot.item.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemberRespository {

    @Autowired
    private SqlSessionTemplate sql;

    //회원가입
    public void saveMember(MemberDTO member) {
        sql.insert("memberSql.saveMember", member);
    }
}
