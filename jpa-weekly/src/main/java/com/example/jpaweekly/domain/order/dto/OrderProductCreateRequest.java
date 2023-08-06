package com.example.jpaweekly.domain.order.dto;

public record OrderProductCreateRequest (
        Long productId,
        int quantity
){
}
