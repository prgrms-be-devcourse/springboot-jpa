package org.programmers.jpaweeklymission.customer.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerCreationRequest(
        @NotBlank
        @Size(min = 1, max = 20)
        String firstName,
        @NotBlank
        @Size(min = 1, max = 20)
        String lastName
) {
}
