package com.example.demo.domain.order;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrderStatus {
    OPENED(1), CANCELED(2);
    
    private int code;

    OrderStatus(int code) {
        this.code = code;
    }

    public static OrderStatus ofCode(int code) {
        return Arrays.stream(OrderStatus.values())
                .filter(v -> v.getCode() == code)
                .findAny()
                .orElseThrow();
    }
}
