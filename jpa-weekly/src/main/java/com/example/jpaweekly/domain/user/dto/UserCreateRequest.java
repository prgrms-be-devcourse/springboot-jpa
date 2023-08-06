package com.example.jpaweekly.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserCreateRequest(
        @NotBlank
        String loginId,
        @NotBlank
        String password,
        @NotBlank
        String nickname
) {
}
