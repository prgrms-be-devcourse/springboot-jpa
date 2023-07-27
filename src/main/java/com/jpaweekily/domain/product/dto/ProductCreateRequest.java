package com.jpaweekily.domain.product.dto;

public record ProductCreateRequest(
        String productName,
        int price
) {
}
