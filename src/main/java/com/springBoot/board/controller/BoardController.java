package com.springBoot.board.controller;

import com.springBoot.board.dto.BoardDTO;
import com.springBoot.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j // 로그
@Controller
@RequestMapping("/board/list")
@RequiredArgsConstructor //생성자 주입
public class BoardController {
    private final BoardService boardService;

    //목록
    @GetMapping
    public String boardList(Model model){
        List<BoardDTO> boardList = boardService.boardList();
        model.addAttribute("boardList", boardList);
        log.info(" boardList={}", boardList);
        log.info(" model={}", model);
        return "board/list";
    }

    //상세
    @GetMapping("/{id}")
    public String boardDetail(@PathVariable("id") Long id, Model model){
        //조회수 증가
        boardService.updateHits(id);

        //상세화면
        BoardDTO boardDetail = boardService.boardDetail(id);
        log.info(" id={}", id);
        model.addAttribute("board", boardDetail);
        log.info(" model={}", boardDetail);

        return "board/detail";
    }

    //등록 페이지 open
    @GetMapping("/insert")
    public String insertVw(){
        return "board/insert";
    }

    //등록
    @PostMapping("/insert")
    public String insert(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
        log.info(" info log={}", boardDTO);
        boardService.insert(boardDTO);
        return "redirect:/board/list";
    }

    //수정 페이지 open
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model){
        BoardDTO boardDetail = boardService.boardDetail(id);
        model.addAttribute("board", boardDetail);
        return "board/update";
    }

    //수정
    @PostMapping("/update/{id}")
    public String update(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
        log.info(" info log={}", boardDTO);
        boardService.update(boardDTO);
        return "redirect:/board/list/{id}";
    }

    //삭제
    @GetMapping("/delete/{id}")
    public String update(@PathVariable("id") Long id){
        boardService.delete(id);
        return "redirect:/board/list";
    }

}
