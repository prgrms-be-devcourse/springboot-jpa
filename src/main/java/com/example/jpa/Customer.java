package com.example.jpa;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String name;
    private String nickname;

    private Customer (String name, String nickname, String password, String username){
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.username = username;
    }

    public static Customer of(CustomerSaveRequestDto requestDto) {
        return new Customer(
                requestDto.name(),
                requestDto.nickname(),
                requestDto.password(),
                requestDto.username()
        );
    }

    public Long updateInfo(CustomerUpdateRequestDto requestDto) {
        this.username = requestDto.username();
        this.password = requestDto.password();
        this.nickname = requestDto.nickname();
        return this.id;
    }
}
