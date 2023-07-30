package com.blackdog.springbootjpa.domain.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerCreateRequest(
        @NotBlank
        @Size(max = 50)
        String name,

        @Size(max = 3)
        int age,

        @NotBlank
        @Email
        @Size(max = 50)
        String email
) {
}
