package com.jpaweekily.domain.order.dto;

import com.jpaweekily.domain.order.Order;
import com.jpaweekily.domain.order.OrderStatus;

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
                order.getCreateAt());
    }
}
