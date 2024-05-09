package com.springBoot.item.service;

import com.springBoot.item.dto.MemberDTO;
import com.springBoot.item.repository.ItemRepository;
import com.springBoot.item.repository.MemberRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRespository memberRespository;

    //회원가입
    public void saveMember(MemberDTO member) {
        memberRespository.saveMember(member);
    }
}
