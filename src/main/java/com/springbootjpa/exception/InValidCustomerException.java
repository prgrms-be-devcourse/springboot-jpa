package com.springbootjpa.exception;

public class InValidCustomerException extends RuntimeException {

    public InValidCustomerException(String message) {
        super(message);
    }

    public InValidCustomerException() {
        this("유효하지 않은 고객입니다.");
    }
}
