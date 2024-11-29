package org.programmers.jpaweeklymission.customer.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerUpdateRequest(
        @NotBlank(message = "First name must not be blank.")
        @Size(min = 1, max = 20, message = "First name must be at least 1 character and no more than 20 characters.")
        String firstName,
        @NotBlank(message = "Last name must not be blank.")
        @Size(min = 1, max = 20, message = "Last name must be at least 1 character and no more than 20 characters.")
        String lastName
) {
}
