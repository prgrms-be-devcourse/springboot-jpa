package com.springbootjpa.exception;

public class NoValidCustomerException extends RuntimeException {

    public NoValidCustomerException(String message) {
        super(message);
    }

    public NoValidCustomerException() {
        this("유효하지 않은 고객입니다.");
    }
}
