package com.example.springbootjpa.golbal.exception;

import com.example.springbootjpa.golbal.ErrorCode;

public class InvalidDomainConditionException extends RuntimeException{

    private final ErrorCode errorCode;

    public InvalidDomainConditionException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}