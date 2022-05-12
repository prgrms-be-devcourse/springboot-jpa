package com.prgms.springbootjpa.exception;

public enum ExceptionMessage {
    FIRST_NAME_FORMAT_EXP_MSG("first name은 1글자 이상 9글자 이하입니다."),
    LAST_NAME_FORMAT_EXP_MSG("lastname은 1글자 이상 5글자 미만입니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
