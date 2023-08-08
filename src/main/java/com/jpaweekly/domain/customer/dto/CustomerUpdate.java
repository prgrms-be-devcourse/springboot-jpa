package com.jpaweekly.domain.customer.dto;

import jakarta.validation.constraints.NotBlank;

public record CustomerUpdate (
        @NotBlank
        String firstName,
        @NotBlank
        String lastName
) {
}
