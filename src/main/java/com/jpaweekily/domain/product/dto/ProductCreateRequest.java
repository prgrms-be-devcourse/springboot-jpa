package com.jpaweekily.domain.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ProductCreateRequest(
        @NotBlank
        String productName,
        @Min(0)
        int price
) {
}
