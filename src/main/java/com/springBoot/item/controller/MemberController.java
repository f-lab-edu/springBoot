package com.springBoot.item.controller;

import com.springBoot.item.dto.MemberDTO;
import com.springBoot.item.service.ItemService;
import com.springBoot.item.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Slf4j // 로그
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor //생성자 주입
public class MemberController {

    private final MemberService memberService;

    //회원가입 페이지 open
    @GetMapping("/add")
    public String addFormVw(@ModelAttribute("member") MemberDTO member) {
        return "members/addMemberForm";
    }

    //회원가입
    @PostMapping("/add")
    public String saveMember(@Valid @ModelAttribute MemberDTO member, BindingResult result) {
        if (result.hasErrors()) {
//            return "members/addMemberForm";
        }
        memberService.saveMember(member);
        return "redirect:/";
    }

}
