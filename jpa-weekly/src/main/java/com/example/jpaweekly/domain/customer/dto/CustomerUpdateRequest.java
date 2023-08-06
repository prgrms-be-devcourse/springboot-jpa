package com.example.jpaweekly.domain.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerUpdateRequest(
        @NotNull
        Long id,
        @NotBlank
        String firstName,
        @NotBlank
        String lastName
) {
}
