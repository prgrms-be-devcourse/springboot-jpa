package com.example.jpa;

public record CustomerUpdateRequestDto(
        String username,
        String password,
        String nickname
) {
}
