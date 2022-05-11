package com.prgms.springbootjpa.exception;

public enum ExceptionMessage {
    NAME_FORMAT_EXP_MSG("이름은 1글자 이상 9글자 이하입니다.");
    
    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
