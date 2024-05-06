package com.springBoot.item.controller;

import com.springBoot.item.dto.ItemDTO;
import com.springBoot.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j // 로그
@Controller
@RequestMapping("/item/list")
@RequiredArgsConstructor //생성자 주입
public class ItemController {
    private final ItemService itemService;

    //목록
    @GetMapping
    public String itemList(Model model){
        List<ItemDTO> itemList = itemService.itemList();
        model.addAttribute("itemList", itemList);
        return "item/list";
    }

    //상세
    @GetMapping("/{itemId}")
    public String itemDetail(@PathVariable("itemId") Long itemId, Model model){
        //조회수 증가
        itemService.updateHits(itemId);

        //상세화면
        ItemDTO itemDetail = itemService.itemDetail(itemId);
        model.addAttribute("item", itemDetail);

        return "item/detail";
    }

    //등록 페이지 open
    @GetMapping("/insert")
    public String itemInsertVw(Model model){
        model.addAttribute("item", new ItemDTO());
        return "item/insert";
    }

    //등록
    @PostMapping("/insert")
    public String itemInsert(ItemDTO itemDTO, RedirectAttributes redirectAttributes) {
        log.info(" insert ={}", itemDTO);
        itemService.itemInsert(itemDTO);
        return "redirect:/item/list";
    }

    //수정 페이지 open
    @GetMapping("/update/{itemId}")
    public String itemUpdateVw(@PathVariable("itemId") Long itemId, Model model){
        ItemDTO itemDetail = itemService.itemDetail(itemId);
        model.addAttribute("item", itemDetail);
        return "item/update";
    }

    //수정
    @PostMapping("/update/{itemId}")
    public String itemUpdate(@PathVariable("itemId") Long itemId, ItemDTO itemDTO, RedirectAttributes redirectAttributes) {
        log.info(" update log={}", itemDTO);
        itemService.itemUpdate(itemDTO);
        return "redirect:/item/list/{itemId}";
    }

    //삭제
    @GetMapping("/delete/{itemId}")
    public String itemDelete(@PathVariable("itemId") Long itemId){
        itemService.itemDelete(itemId);
        return "redirect:/item/list";
    }

}
