package com.programmers.jpa.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateRequest(@NotNull Long id, @NotBlank String firstName, @NotBlank String lastName) {
}
