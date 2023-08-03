package com.jpaweekly.domain.customer.dto;

import jakarta.validation.constraints.NotBlank;

public record CustomerRequest (
        @NotBlank
        String firstName,
        @NotBlank
        String lastName
){
}
