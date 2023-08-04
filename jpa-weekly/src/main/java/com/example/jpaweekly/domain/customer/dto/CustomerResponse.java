package com.example.jpaweekly.domain.customer.dto;

public record CustomerResponse (
        Long id,
        String firstName,
        String lastName
) {
}
