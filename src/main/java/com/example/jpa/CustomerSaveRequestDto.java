package com.example.jpa;

public record CustomerSaveRequestDto (
        String username,
        String nickname,
        String password,
        String name
){
}
