package com.example.jpaweekly.domain.user.dto;

public record UserCreateRequest(
        String loginId,
        String password,
        String nickname
) {
}
