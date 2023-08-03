package org.programmers.jpaweeklymission.customer.presentation.dto;

import jakarta.validation.constraints.Size;

public record CustomerUpdateRequest(
        @Size(min = 1, max = 20, message = "First name must be at least 1 character and no more than 20 characters.")
        String firstName,
        @Size(min = 1, max = 20, message = "Last name must be at least 1 character and no more than 20 characters.")
        String lastName
) {
}
