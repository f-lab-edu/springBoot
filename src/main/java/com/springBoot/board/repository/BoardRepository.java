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

    public void save(BoardDTO boardDTO) {
        sql.insert("BoardSql.save", boardDTO);
    }

    public List<BoardDTO> findAll() {
        return sql.selectList("BoardSql.findAll");
    }

    public void updateHits(Long id) {
        sql.update("BoardSql.updateHits", id);
    }

    public BoardDTO findById(Long id) {
        return sql.selectOne("BoardSql.findById");
    }
}
