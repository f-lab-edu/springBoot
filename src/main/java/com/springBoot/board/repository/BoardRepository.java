package com.springBoot.board.repository;

import com.springBoot.board.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor //생성자 주입
public class BoardRepository {
    private final SqlSessionTemplate sql;

    //목록
    public List<BoardDTO> boardList() {
        return sql.selectList("BoardSql.boardList");
    }

    //조회수 증가
    public void updateHits(Long id) {
        sql.update("BoardSql.updateHits", id);
    }

    //상세
    public BoardDTO boardDetail(Long id) {
        return sql.selectOne("BoardSql.boardDetail", id);
    }

    //등록
    public void insert(BoardDTO boardDTO) {
        sql.insert("BoardSql.insert", boardDTO);
    }

    //수정
    public void update(BoardDTO boardDTO) {
        sql.update("BoardSql.update", boardDTO);
    }

    public void delete(Long id) {
        sql.delete("BoardSql.delete", id);
    }
}
