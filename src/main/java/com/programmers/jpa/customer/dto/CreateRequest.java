package com.programmers.jpa.customer.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateRequest(@NotBlank String firstName, @NotBlank String lastName) {
}
