package com.programmers.jpamission.global.exception;

public class InvalidNameRequest extends RuntimeException {

    public InvalidNameRequest(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
