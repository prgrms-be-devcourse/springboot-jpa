package org.programmers.jpaweeklymission.customer.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerUpdateRequest(
        @NotBlank
        @Size(max = 20)
        String firstName,
        @NotBlank
        @Size(max = 20)
        String lastName
) {
}
