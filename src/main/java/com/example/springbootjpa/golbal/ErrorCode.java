package com.example.springbootjpa.golbal;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //고객
    INVALID_USERNAME(HttpStatus.BAD_REQUEST, "잘못된 이름입니다."),
    INVALID_ADDRESS(HttpStatus.BAD_REQUEST, "잘못된 주소입니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
