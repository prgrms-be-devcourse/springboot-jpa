package com.example.springbootjpaweekly3.domain.contant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PREPARATION(0, "준비"),
    SHIPPING(1, "배송"),
    ARRIVAL(2, "도착"),
    CANCELLATION(3, "취소");

    private final Integer value;

    private final String typeName;
}