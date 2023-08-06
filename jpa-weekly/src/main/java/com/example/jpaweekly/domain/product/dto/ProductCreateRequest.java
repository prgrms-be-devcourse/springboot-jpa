package com.example.jpaweekly.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductCreateRequest(
        @NotBlank
        String productName,
        @NotNull
        int price
) {
}
