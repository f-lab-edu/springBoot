package com.springBoot.item.service;

import com.springBoot.item.dto.ItemDTO;
import com.springBoot.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    //목록
    public List<ItemDTO> itemList() {
        return itemRepository.itemList();
    }

    //조회수 증가
    public void updateHits(Long id) {
        itemRepository.updateHits(id);
    }

    //상세
    public ItemDTO itemDetail(Long id) {
        return itemRepository.itemDetail(id);
    }

    //파일상세
    public ItemDTO fileDetail(String fileId) {
        return itemRepository.fileDetail(fileId);
    }

    //등록
    public void itemInsert(ItemDTO itemDTO) throws IOException {
        if(itemDTO.getFileName()!=null){
            itemRepository.fileInsert(itemDTO);
        }
        itemRepository.itemInsert(itemDTO);
    }

    //수정
    public void itemUpdate(ItemDTO itemDTO) {
        itemRepository.itemUpdate(itemDTO);
    }


    public void itemDelete(Long id) {
        itemRepository.itemDelete(id);
    }
}
