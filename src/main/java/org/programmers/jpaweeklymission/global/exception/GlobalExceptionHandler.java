package org.programmers.jpaweeklymission.global.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
        log.warn(e.getMessage(), e);
        return ErrorResponse.newErrorResponse(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn(e.getMessage(), e);
        // TODO:
        //  이 에러 매세지를 출력하는 방법이 여러 방법 있을거 같은데 어떻게 하시나요?
        //  저는 일단 이게 코드로 짧고 가독성도 좋아서 이렇게 했는데, ExceptionHandler 라서 null 체크 안 해줘도 되는데 intellij 노란줄이 계속 거슬립니다.
        //  그리고 굳이 에러 메세지를 자세하게 해줘야 되나 생각이 듭니다. 아래 HttpMessageNotReadableException 와 묶어서 에러 메세지를 동일한 "Invalid Request." 로 하는 거 어떤가요?
        return ErrorResponse.newErrorResponse(e.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn(e.getMessage(), e);
        return ErrorResponse.badRequest();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ErrorResponse.internalServerError();
    }
}
