package com.prgms.springbootjpa.exception;

public class InvalidNameLengthException extends RuntimeException {

    private final ExceptionMessage exceptionMessage;

    public InvalidNameLengthException(ExceptionMessage message) {
        this.exceptionMessage = message;
    }

    @Override
    public String getMessage() {
        return exceptionMessage.getMessage();
    }
}
