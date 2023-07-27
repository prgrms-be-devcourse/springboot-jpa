package kr.co.springbootjpaweeklymission.global.error.handler;

import kr.co.springbootjpaweeklymission.global.error.model.ErrorResponse;
import kr.co.springbootjpaweeklymission.global.error.model.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class NotValidExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("======= Handle MethodArgumentNotValidException =======", e);
        return handleExceptionInternal(e);
    }

    private ResponseEntity<ErrorResponse> handleExceptionInternal(BindException e) {
        return ResponseEntity
                .status(ErrorResult.INVALID_PARAMETER.getHttpStatus())
                .body(makeResponseErrorFormat(e));
    }

    private ErrorResponse makeResponseErrorFormat(BindException e) {
        final List<ErrorResponse.ValidationException> exceptions = e.getBindingResult().getFieldErrors().stream()
                .map(ErrorResponse.ValidationException::of)
                .toList();
        return ErrorResponse.builder()
                .message(ErrorResult.INVALID_PARAMETER.getMessage())
                .status(ErrorResult.INVALID_PARAMETER.getHttpStatus())
                .validationExceptions(exceptions)
                .build();
    }
}
