package kr.co.springbootjpaweeklymission.global.error.exception;

import kr.co.springbootjpaweeklymission.global.error.model.ErrorResult;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private ErrorResult errorResult;

    public EntityNotFoundException(ErrorResult errorResult) {
        super(errorResult.getMessage());
        this.errorResult = errorResult;
    }
}
