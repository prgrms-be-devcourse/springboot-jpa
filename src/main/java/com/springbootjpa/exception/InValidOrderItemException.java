package com.springbootjpa.exception;

public class InValidOrderItemException extends RuntimeException {

    public InValidOrderItemException(String message) {
        super(message);
    }

    public InValidOrderItemException() {
        this("유효하지 않은 상품주문입니다.");
    }
}
