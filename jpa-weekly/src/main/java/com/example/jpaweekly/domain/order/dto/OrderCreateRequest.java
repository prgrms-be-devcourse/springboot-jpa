package com.example.jpaweekly.domain.order.dto;

import java.util.List;

public record OrderCreateRequest(
        String address,
        List<OrderProductCreateRequest> orderProducts
) {
}
