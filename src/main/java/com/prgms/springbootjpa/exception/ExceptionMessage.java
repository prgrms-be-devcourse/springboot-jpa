package com.prgms.springbootjpa.exception;

public enum ExceptionMessage {


    CUSTOMER_FIRST_NAME_FORMAT_EXP_MSG("first name은 1글자 이상 9글자 이하입니다."),
    CUSTOMER_LAST_NAME_FORMAT_EXP_MSG("lastname은 1글자 이상 5글자 미만입니다."),

    NAME_LENGTH_EXP_MSG("이름의 길이는 1 이상 30 이하 입니다."),
    NICK_NAME_LENGTH_EXP_MSG("닉네임의 길이는 1 이상 30 이하 입니다."),
    PRICE_RANGE_EXP_MSG("가격의 범위는 1000이상 1000000이하 입니다."),
    QUANTITY_RANGE_EXP_MSG("가능한 수량 범위는 1이상 1000이하 입니다."),
    NO_AVAILABLE_QUANTITY_EXP_MSG("남은 재고가 부족합니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
