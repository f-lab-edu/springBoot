package com.springBoot.board.controller;

import com.springBoot.board.dto.BoardDTO;
import com.springBoot.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor //생성자 주입
public class BoardController {
    private final BoardService boardService;

    //작성 페이지 open
    @GetMapping("save")
    public String save(){
        return "save";
    }

    //작성 글 저장
    @PostMapping("/save")
    public String save(BoardDTO boardDTO){
        log.info(" info log={}", boardDTO);
        boardService.save(boardDTO);
        return "index";
    }

    //목록 출력
    @GetMapping("/list")
    public String findAll(Model model){
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

    //게시글 조회
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model){
        //조회수 증가
        boardService.updateHits(id);

        //상세화면
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);

        return "detail";
    }
}
