package com.springBoot.item.controller;

import com.springBoot.item.dto.ItemDTO;
import com.springBoot.item.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j // 로그
@Controller
@RequestMapping("/item/list")
@RequiredArgsConstructor //생성자 주입
public class ItemController {
    private final ItemService itemService;
//    private final FileStore fileStore;

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
        ItemDTO fileDetail = itemService.fileDetail(itemDetail.getFileId());
        model.addAttribute("file", fileDetail);

        return "item/detail";
    }

    //등록 페이지 open
    @GetMapping("/insert")
    public String itemInsertVw(Model model){
        model.addAttribute("item", new ItemDTO());
        return "item/insert";
    }

    //등록
    //@Validated : ItemDTO 검증을 항상 해줌
    @PostMapping("/insert")
    public String itemInsert(@Validated @ModelAttribute("itemDTO") ItemDTO itemDTO, BindingResult bindingResult, @RequestParam("itemFile") MultipartFile uploadFile, HttpServletRequest request) throws IOException {

        //검증 실패
        if (bindingResult.hasErrors()) {
//            return "item/insert"; //org.thymeleaf.exceptions.TemplateInputException: An error happened during template parsing (template: "class path resource [templates/item/insert.html]")
        }

        //파일업로드
        if (!uploadFile.isEmpty()) {
            String path = "C:" + File.separator + "study" + File.separator + "uploadFile"; // 업로드 경로
            String originalFilename = uploadFile.getOriginalFilename();   // 기본 파일명
            String fileName = createStoreFileName(originalFilename); // 서버에 저장하는 파일명 생성
            String filepath = path + File.separator + fileName;      // 경로 + 서버파일명

            File file = new File(filepath);
            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bos.write(uploadFile.getBytes());
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
                return "file upload fail";
            }

            itemDTO.setFileId("file_" + UUID.randomUUID().toString());
            itemDTO.setFileName(fileName);
            itemDTO.setOriFileName(originalFilename);
            itemDTO.setFilePath(filepath);
        }

        itemService.itemInsert(itemDTO);

        return "redirect:/item/list";
    }

    // 서버에 저장하는 파일명 생성
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename); // 확장자 추출
        String uuid = UUID.randomUUID().toString(); // UUID 생성
        return uuid + "." + ext; // 고유한 파일명 생성
    }

    // 파일 확장자 추출
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
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
    public String itemUpdate(@PathVariable("itemId") Long itemId, @Validated @ModelAttribute("itemDTO") ItemDTO itemDTO, BindingResult bindingResult) {

        //검증 실패
        if (bindingResult.hasErrors()) {
//            return "item/update"; //org.thymeleaf.exceptions.TemplateInputException: An error happened during template parsing (template: "class path resource [templates/item/insert.html]")
        }

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
