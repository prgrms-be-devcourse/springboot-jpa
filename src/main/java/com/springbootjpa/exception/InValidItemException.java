package com.springbootjpa.exception;

public class InValidItemException extends RuntimeException {

    public InValidItemException(String message) {
        super(message);
    }

    public InValidItemException() {
        this("유효하지 않은 상품입니다.");
    }
}
