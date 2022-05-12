package com.example.demo.domain;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrderStatus {
    OPENED("1"), CANCELED("2");
    
    private String code;

    OrderStatus(String code) {
        this.code = code;
    }

    public static OrderStatus ofCode(String code) {
        return Arrays.stream(OrderStatus.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow();
    }
}
