package com.jpaweekily.domain.order.dto;

public record OrderProductCreate(
    Long productId,
    int quantity
) {
}
