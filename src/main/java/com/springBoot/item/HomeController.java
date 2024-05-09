package com.springBoot.item;

import com.springBoot.item.argumentresolver.Login;
import com.springBoot.item.dto.MemberDTO;
import com.springBoot.item.repository.LoginRespository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final LoginRespository loginRespository;


    // 스프링 인터셉터 를 이용한 로그인 인증 체크
//    @GetMapping("/")
    public String homeLoginInterceptor(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberDTO loginMember, Model model) {
        //세션에 회원 데이터가 없으면 home으로 이동
        if (loginMember == null) {
            return "home";
        }
        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    // LoginArgumentResolver(@Login) 를 이용한 로그인 인증 체크
    @GetMapping("/")
    public String homeLogin(@Login MemberDTO loginMember, Model model) {
        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}
