package kr.co.springbootjpaweeklymission.global.common;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Regexp {
    public static final String EMAIL_PATTERN = "^([A-Z|a-z|0-9](\\.|_){0,1})+[A-Z|a-z|0-9]\\@([A-Z|a-z|0-9])+((\\.){0,1}[A-Z|a-z|0-9]){2}\\.[a-z]{2,3}$";
    public static final String KOREA_CELL_PHONE_PATTERN = "^01([0|1|6|7|8|9]?)-([0-9]{3,4})-([0-9]{4})$";
}
