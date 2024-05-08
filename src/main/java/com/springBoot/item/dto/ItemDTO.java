package com.springBoot.item.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

import java.util.List;

@Getter
@Setter
@ToString
public class ItemDTO {

    private Long itemId;

    @NotBlank
    private String itemDetail;

    @NotBlank
    private String itemName;

    @NotBlank
    private String itemSellStatus;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
    @Max(9999)
    private Integer quantity;

    private int itemHits;
    private String createdAt;
    private Boolean openYn;         //판매여부

}
