package com.example.demo.domain;

import java.util.Arrays;

public enum OrderStatus {
    ACCEPTED("accepted"),
    SHIPPED("shipped"),
    DELIVERED("delivered"),
    CANCELLED("cancelled"),
    ;

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }

    public static OrderStatus toOrderStatus(String status) {
        return Arrays.stream(values()).filter((e)-> e.toString().equals(status)).findFirst().orElseThrow(()-> new RuntimeException("Input correct OrderStatus: "+ values()));
    }
}
