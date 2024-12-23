package com.shop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter@Getter
@ToString
public class CartDetailDto {

    private Long cartItemId; //장바구니 상품 Id
    private String itemNm;  //상품명
    private int price; //금액
    private int count; //수량

    private String imgUrl; //상품이미지 경로

    public CartDetailDto(Long cartItemId, String itemNm, int price, int count, String imgUrl) {
        this.cartItemId = cartItemId;
        this.itemNm = itemNm;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }
}
