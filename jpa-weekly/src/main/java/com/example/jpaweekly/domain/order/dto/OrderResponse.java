package com.example.jpaweekly.domain.order.dto;

import com.example.jpaweekly.domain.order.OrderStatus;

public record OrderResponse(
        Long id,
        String address,
        OrderStatus orderStatus,
        String userNickname
) {
}
