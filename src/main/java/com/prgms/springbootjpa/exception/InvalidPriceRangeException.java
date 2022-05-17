package com.prgms.springbootjpa.exception;

public class InvalidPriceRangeException extends RuntimeException {

    private final ExceptionMessage exceptionMessage;

    public InvalidPriceRangeException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String getMessage() {
        return exceptionMessage.getMessage();
    }
}
