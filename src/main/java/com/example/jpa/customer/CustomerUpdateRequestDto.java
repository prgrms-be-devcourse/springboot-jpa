package com.example.jpa.customer;

public record CustomerUpdateRequestDto(
        String username,
        String password,
        String nickname
) {
}
