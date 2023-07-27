package kr.co.springbootjpaweeklymission.global.error.handler;

import kr.co.springbootjpaweeklymission.global.error.exception.DuplicateException;
import kr.co.springbootjpaweeklymission.global.error.model.ErrorResponse;
import kr.co.springbootjpaweeklymission.global.error.model.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateException.class)
    protected ResponseEntity<ErrorResponse> handleDuplicatedException(DuplicateException e) {
        log.warn("======= Handle DuplicateException =======", e);
        return handleExceptionInternal(e.getErrorResult());
    }

    private ResponseEntity<ErrorResponse> handleExceptionInternal(ErrorResult errorResult) {
        return ResponseEntity
                .status(errorResult.getHttpStatus())
                .body(makeResponseErrorFormat(errorResult));
    }

    private ErrorResponse makeResponseErrorFormat(ErrorResult errorResult) {
        return ErrorResponse.builder()
                .message(errorResult.getMessage())
                .status(errorResult.getHttpStatus())
                .build();
    }
}
