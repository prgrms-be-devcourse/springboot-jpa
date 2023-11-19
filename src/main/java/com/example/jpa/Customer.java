package com.example.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String name;
    private String nickname;
    @Builder
    private Customer (String name, String nickname, String password, String username){
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.username = username;
    }

    public static Customer of(CustomerSaveRequestDto requestDto) {
        return Customer.builder()
                .name(requestDto.name())
                .nickname(requestDto.nickname())
                .password(requestDto.password())
                .username(requestDto.username())
                .build();
    }

    public Long updateInfo(CustomerUpdateRequestDto requestDto) {
        this.username = requestDto.username();
        this.password = requestDto.password();
        this.nickname = requestDto.nickname();
        return this.id;
    }
}
