package com.programmers.jpamission.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    INVALID_FIRST_NAME_REQUEST("유효하지 않은 이름 길이입니다.\n"),
    INVALID_LAST_NAME_REQUEST("유효하지 않은 성 길이입니다.\n");

    private final String message;
}
