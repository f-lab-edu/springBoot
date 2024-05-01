package com.springBoot.item.dto;

public enum BoardType {

    Y("도서"), FOOD("음식"), ETC("기타");

    private final String description;

    BoardType(String description) {
        this.description = description;
    }

}
