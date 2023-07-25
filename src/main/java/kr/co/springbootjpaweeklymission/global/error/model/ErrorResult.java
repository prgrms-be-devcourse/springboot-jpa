package kr.co.springbootjpaweeklymission.global.error.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorResult {
    DUPLICATED_EMAIL("이메일이 중복되었습니다.", HttpStatus.BAD_REQUEST),
    DUPLICATED_CELL_PHONE("핸드폰 번호가 중복되었습니다.", HttpStatus.BAD_REQUEST),
    INVALID_PARAMETER("파라미터 값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
