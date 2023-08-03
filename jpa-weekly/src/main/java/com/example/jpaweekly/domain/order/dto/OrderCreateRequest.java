package com.example.jpaweekly.domain.order.dto;

import com.example.jpaweekly.domain.order.OrderStatus;
import com.example.jpaweekly.domain.user.User;

import java.util.List;

public record OrderCreateRequest(
        String address,
        List<OrderProductCreateRequest> orderProducts
) {
}
