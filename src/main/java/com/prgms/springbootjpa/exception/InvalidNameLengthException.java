package com.prgms.springbootjpa.exception;

public class InvalidNameLengthException extends RuntimeException {

    public InvalidNameLengthException(String message) {
        super(message);
    }
}
