package org.programmers.jpaweeklymission.global.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Server internal error.";
    private static final String BAD_REQUEST_MESSAGE = "Invalid Request.";
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
