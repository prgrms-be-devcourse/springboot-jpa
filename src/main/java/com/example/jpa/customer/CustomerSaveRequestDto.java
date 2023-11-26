package com.example.jpa.customer;

public record CustomerSaveRequestDto (
        String username,
        String nickname,
        String password,
        String name
){
}
