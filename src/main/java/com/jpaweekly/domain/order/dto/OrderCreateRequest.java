package com.jpaweekly.domain.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderCreateRequest(
        @NotBlank
        String address,
        @NotNull
        List<OrderProductCreate> orderProductCreateList
) {
}
