package com.springBoot.item.service;

import com.springBoot.item.dto.LoginDTO;
import com.springBoot.item.dto.MemberDTO;
import com.springBoot.item.repository.LoginRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginRespository loginRespository;

    //로그인
    public MemberDTO login(LoginDTO loginDTO) {
        return loginRespository.login(loginDTO);
    }
}
