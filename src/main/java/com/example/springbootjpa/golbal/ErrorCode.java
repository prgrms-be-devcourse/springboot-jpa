package com.example.springbootjpa.golbal;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //고객
    INVALID_USERNAME_OR_ADDRESS(HttpStatus.BAD_REQUEST, "특수문자를 제외한 문자를 입력해주세요."),

    //주문아이템
    INVALID_ORDER_PRICE(HttpStatus.BAD_REQUEST, "주문 금액은 0보다 작을 수 없습니다"),
    INVALID_ORDER_QUANTITY(HttpStatus.BAD_REQUEST, "주문 수량은 0보다 작을 수 없습니다"),
    INVALID_ORDER_ITEM_QUANTITY(HttpStatus.BAD_REQUEST, "재고보다 많은 수량을 주문할 수 없습니다"),
    INVALID_ORDER_ITEMS(HttpStatus.BAD_REQUEST, "주문 상품이 비었습니다."),

    //아이템,
    INVALID_ITEM_PRICE(HttpStatus.BAD_REQUEST, "상품 금액은 0보다 작을 수 없습니다"),
    INVALID_ITEM_QUANTITY(HttpStatus.BAD_REQUEST, "상품 수량은 0보다 작을 수 없습니다."),
    INVALID_KEYBOARD_COLOR(HttpStatus.BAD_REQUEST, "키보드 색깔을 입력해주세요."),
    INVALID_MOUSE_COLOR(HttpStatus.BAD_REQUEST, "마우스 색깔을 입력해주세요."),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "재고가 부족합니다");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
