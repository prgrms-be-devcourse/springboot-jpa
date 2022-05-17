package com.prgms.springbootjpa.exception;

public class InvalidQuantityRangeException extends RuntimeException {

    private final ExceptionMessage exceptionMessage;

    public InvalidQuantityRangeException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String getMessage() {
        return exceptionMessage.getMessage();
    }
}
