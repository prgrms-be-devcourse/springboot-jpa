package com.prgrms.springbootjpa.global.util;

public class Validator {
    public static boolean validateFieldLength(String field, int min, int max) {
        if(min <= field.length() && field.length() <= max) {
            return true;
        } else {
            return false;
        }
    }
}
