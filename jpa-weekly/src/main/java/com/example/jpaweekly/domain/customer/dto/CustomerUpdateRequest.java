package com.example.jpaweekly.domain.customer.dto;

public record CustomerUpdateRequest(
        Long id,
        String firstName,
        String lastName
) {
}
