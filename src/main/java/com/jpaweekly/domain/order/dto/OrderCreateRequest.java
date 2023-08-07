package com.jpaweekly.domain.order.dto;

import com.jpaweekly.domain.order.Order;
import com.jpaweekly.domain.order.OrderStatus;
import com.jpaweekly.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;


public record OrderCreateRequest(
        @NotBlank
        String address,
        @NotNull
        List<OrderProductCreate> orderProductCreateList
) {
    public Order toEntity(User user) {
        return Order.builder()
                .address(this.address)
                .orderStatus(OrderStatus.READY_FOR_DELIVERY)
                .user(user)
                .build();
    }
}
