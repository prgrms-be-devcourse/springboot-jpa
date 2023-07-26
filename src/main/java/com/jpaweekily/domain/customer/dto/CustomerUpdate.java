package com.jpaweekily.domain.customer.dto;

public record CustomerUpdate (
        Long id,
        String firstName,
        String lastName
) {
}
