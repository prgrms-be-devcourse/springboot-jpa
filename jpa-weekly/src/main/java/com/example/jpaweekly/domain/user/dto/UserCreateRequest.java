package com.example.jpaweekly.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(
        @NotBlank
        String loginId,
        @NotBlank
        String password,
        @NotBlank
        String nickname
) {
}
