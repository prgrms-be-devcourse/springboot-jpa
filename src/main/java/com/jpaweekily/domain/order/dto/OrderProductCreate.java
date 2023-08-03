package com.jpaweekily.domain.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderProductCreate(
        @NotNull
        Long productId,
        @NotBlank
        int quantity
) {
}
