package com.example.springbootjpa.golbal.exception;

import com.example.springbootjpa.golbal.ErrorCode;

public class DomainException extends RuntimeException{

    private final ErrorCode errorCode;

    public DomainException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}