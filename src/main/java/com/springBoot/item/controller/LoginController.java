package com.springBoot.item.controller;

import com.springBoot.item.dto.LoginDTO;
import com.springBoot.item.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j // 로그
@Controller
@RequiredArgsConstructor //생성자 주입
public class LoginController {

    private final LoginService loginService;
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginDTO loginDTO) {
        return "login/loginForm";
    }
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginDTO loginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        boolean isAuthenticated = loginService.login(loginDTO);
        if(isAuthenticated){ //로그인 성공
            return "redirect:/item/list";
        }else{
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }
    }
}
