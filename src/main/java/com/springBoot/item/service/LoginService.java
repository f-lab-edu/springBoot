package com.springBoot.item.service;

import com.springBoot.item.dto.LoginDTO;
import com.springBoot.item.dto.MemberDTO;
import com.springBoot.item.repository.LoginRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //생성자 주입
public class LoginService {

    private final LoginRespository loginRespository;

    //로그인
    public MemberDTO login(LoginDTO loginDTO) {
        return loginRespository.login(loginDTO);
    }
}
