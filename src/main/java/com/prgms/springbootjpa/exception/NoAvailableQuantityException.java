package com.prgms.springbootjpa.exception;

public class NoAvailableQuantityException extends RuntimeException {

    private final ExceptionMessage exceptionMessage;

    public NoAvailableQuantityException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String getMessage() {
        return exceptionMessage.getMessage();
    }
}
