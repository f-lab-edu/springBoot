package com.springBoot.item.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ItemDTO {

    private Long itemId;
    private String itemDetail;
    private String itemName;
    private String itemSellStatus;
    private int price;
    private int quantity;
    private int itemHits;
    private String createdAt;
    private Boolean openYn;         //판매여부

//    private Long id;              //글번호
//    private String boardWriter;   //작성자
//    private String boardPass;     //비밀번호
//    private String boardTitle;    //글제목
//    private String boardContents; //글내용
//    private int boardHits;        //조회수
//    private String createdAt;     //작성시간
//    private Boolean notice;       //공지여부
//    private String useYn;         //사용여부
//
//    private List<String> regions; //등록 지역
//    private BoardType boardType;  //상품 종류
//    private String deliveryCode;  //배송 방식
}
