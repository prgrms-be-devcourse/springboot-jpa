package kr.co.springbootjpaweeklymission.global.error.exception;

import kr.co.springbootjpaweeklymission.global.error.model.ErrorResult;

public class DuplicateException extends RuntimeException {
    private ErrorResult errorResult;

    public DuplicateException(ErrorResult errorResult) {
        super(errorResult.getMessage());
        this.errorResult = errorResult;
    }
}
