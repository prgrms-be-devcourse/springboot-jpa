package com.programmers.springbootjpa.global.exception;

public class InvalidRequestValueException extends IllegalArgumentException {

    public InvalidRequestValueException(String message) {
        super(message);
    }
}
