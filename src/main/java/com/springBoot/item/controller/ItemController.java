package com.springBoot.item.controller;

import com.springBoot.item.dto.ItemDTO;
import com.springBoot.item.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j // 로그
@Controller
@RequestMapping("/item/list")
public class ItemController {

    @Autowired
    private ItemService itemService;

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
            String path = File.separator + "study" + File.separator + "uploadFile"; // 업로드 경로
            String originalFilename = uploadFile.getOriginalFilename();   // 기본 파일명
            String fileName = createStoreFileName(originalFilename); // 서버에 저장하는 파일명 생성
            String filepath = path + File.separator + fileName;      // 경로 + 서버파일명

            File file = new File("C:" + filepath);
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

    // 파일 다운로드
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {

        ItemDTO itemDTO = itemService.fileDetail(fileId);

        if (itemDTO == null) {
            return ResponseEntity.notFound().build(); //파일이 없으면 404 반환
        }

        String filepath = itemDTO.getFilePath();

        try {
            Path path = Paths.get(filepath); // 파일 경로를 Path 객체로 변환
            Resource resource = new UrlResource(path.toUri()); // Resource 객체 생성

            if (resource.exists() && resource.isReadable()) { // 파일이 존재하는 경우
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + itemDTO.getOriFileName() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //상세 이미지 띄우기
    @GetMapping("/study/uploadFile/{fileName:.+}") // .+ : 확장자 포함
    public ResponseEntity<Resource> displayImage(@PathVariable String fileName) throws MalformedURLException {
        Path path = Paths.get("C:/study/uploadFile/" + fileName);
        Resource resource = new UrlResource(path.toUri()); // 로컬 파일을 URL 형식으로 변환

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("Could not read the image file: " + fileName);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG); // 이미지 타입에 맞게 설정 (브라우저가 이미지로 인식할 수 있도록!)

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    //수정 페이지 open
    @GetMapping("/update/{itemId}")
    public String itemUpdateVw(@PathVariable("itemId") Long itemId, Model model){
        ItemDTO itemDetail = itemService.itemDetail(itemId);
        model.addAttribute("item", itemDetail);
        ItemDTO fileDetail = itemService.fileDetail(itemDetail.getFileId());
        model.addAttribute("file", fileDetail);
        return "item/update";
    }

    //수정
    @PostMapping("/update/{itemId}")
    public String itemUpdate(@PathVariable("itemId") Long itemId, @Validated @ModelAttribute("itemDTO") ItemDTO itemDTO, BindingResult bindingResult, @RequestParam("itemFile") MultipartFile uploadFile, HttpServletRequest request) throws IOException {

        //검증 실패
        if (bindingResult.hasErrors()) {
//            return "item/update"; //org.thymeleaf.exceptions.TemplateInputException: An error happened during template parsing (template: "class path resource [templates/item/insert.html]")
        }
//
//        //파일업로드
//        if (!uploadFile.isEmpty()) {
//            String path = File.separator + "study" + File.separator + "uploadFile"; // 업로드 경로
//            String originalFilename = uploadFile.getOriginalFilename();   // 기본 파일명
//            String fileName = createStoreFileName(originalFilename); // 서버에 저장하는 파일명 생성
//            String filepath = path + File.separator + fileName;      // 경로 + 서버파일명
//
//            File file = new File("C:" + filepath);
//            try {
//                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//                bos.write(uploadFile.getBytes());
//                bos.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//                return "file upload fail";
//            }
//
//            itemDTO.setFileId("file_" + UUID.randomUUID().toString());
//            itemDTO.setFileName(fileName);
//            itemDTO.setOriFileName(originalFilename);
//            itemDTO.setFilePath(filepath);
//        }

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
