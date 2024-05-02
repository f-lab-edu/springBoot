package com.springBoot.item.service;

import com.springBoot.item.dto.ItemDTO;
import com.springBoot.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor //생성자 주입
public class ItemService {
    private final ItemRepository itemRepository;
    private static long sequence = 0L;

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

    //등록
    public void itemInsert(ItemDTO itemDTO) {
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
