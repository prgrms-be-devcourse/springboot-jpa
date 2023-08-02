package com.example.jpaweekly.domain.product.dto;

public record ProductCreateRequest(
        String productName,
        int price
) {
}
