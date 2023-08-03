package org.programmers.jpaweeklymission.global.exception;

public class ErrorResponse {
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "서버 내부 에러입니다.";
    private static final String BAD_REQUEST_MESSAGE = "잘못된 요청입니다.";
    private final String message;

    private ErrorResponse(String message) {
        this.message = message;
    }

    public static ErrorResponse newErrorResponse(String message) {
        return new ErrorResponse(message);
    }

    public static ErrorResponse badRequest() {
        return new ErrorResponse(BAD_REQUEST_MESSAGE);
    }

    public static ErrorResponse internalServerError() {
        return new ErrorResponse(INTERNAL_SERVER_ERROR_MESSAGE);
    }
}
