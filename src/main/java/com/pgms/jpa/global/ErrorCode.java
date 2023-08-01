package com.pgms.jpa.global;

public enum ErrorCode {
    NO_SUCH_ELEMENT(400, "id에 해당하는 결과 값이 없습니다.");

    private final int httpStatusCode;
    private final String msg;

    ErrorCode(int httpStatusCode, String msg) {
        this.httpStatusCode = httpStatusCode;
        this.msg = msg;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getMsg() {
        return msg;
    }
}
