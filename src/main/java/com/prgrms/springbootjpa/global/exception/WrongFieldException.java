package com.prgrms.springbootjpa.global.exception;

public class WrongFieldException extends RuntimeException{
    private String fieldName;
    private Object value;
    private String reason;

    public WrongFieldException(String fieldName, Object value, String reason) {
        super(reason);
        this.fieldName = fieldName;
        this.value = value;
        this.reason = reason;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getValue() {
        return value;
    }

    public String getReason() {
        return reason;
    }
}
