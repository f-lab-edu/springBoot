package com.springBoot.board.service;

import com.springBoot.board.dto.BoardDTO;
import com.springBoot.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor //생성자 주입
public class BoardService {
    private final BoardRepository boardRepository;
    private static long sequence = 0L;

    //목록
    public List<BoardDTO> boardList() {
        return boardRepository.boardList();
    }

    //조회수 증가
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    //상세
    public BoardDTO boardDetail(Long id) {
        return boardRepository.boardDetail(id);
    }

    //등록
    public void insert(BoardDTO boardDTO) {
        boardRepository.insert(boardDTO);
    }

    //수정
    public void update(BoardDTO boardDTO) {
        boardRepository.update(boardDTO);
    }


    public void delete(Long id) {
        boardRepository.delete(id);
    }
}
