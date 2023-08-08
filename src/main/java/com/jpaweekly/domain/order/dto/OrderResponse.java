package com.jpaweekly.domain.order.dto;

import com.jpaweekly.domain.order.Order;
import com.jpaweekly.domain.order.OrderStatus;

import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        String nickname,
        String address,
        OrderStatus orderStatus,
        LocalDateTime createdAt
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(order.getId(),
                order.getUser().getNickname(),
                order.getAddress(),
                order.getOrderStatus(),
                order.getCreatedAt());
    }
}
