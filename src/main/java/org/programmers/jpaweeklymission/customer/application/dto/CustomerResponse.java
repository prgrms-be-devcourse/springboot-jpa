package org.programmers.jpaweeklymission.customer.application.dto;

import lombok.Builder;

@Builder
public record CustomerResponse(
        Long id,
        String firstName,
        String lastName
) {
}
