package com.springBoot.item.repository;

import com.springBoot.item.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemRepository {

    @Autowired
    private SqlSessionTemplate sql;

    //목록
    public List<ItemDTO> itemList() {
        return sql.selectList("itemSql.itemList");
    }

    //조회수 증가
    public void updateHits(Long id) {
        sql.update("itemSql.updateHits", id);
    }

    //상세
    public ItemDTO itemDetail(Long id) {
        return sql.selectOne("itemSql.itemDetail", id);
    }

    //파일상세
    public ItemDTO fileDetail(String fileId) {
        return sql.selectOne("itemSql.fileDetail", fileId);
    }

    //등록
    public void itemInsert(ItemDTO itemDTO) {
        sql.insert("itemSql.itemInsert", itemDTO);
    }

    //이미지 등록
    public void fileInsert(ItemDTO itemDTO) {
        sql.insert("itemSql.fileInsert", itemDTO);
    }

    //수정
    public void itemUpdate(ItemDTO itemDTO) {
        sql.update("itemSql.itemUpdate", itemDTO);
    }

    public void itemDelete(Long id) {
        sql.delete("itemSql.itemDelete", id);
    }
}
