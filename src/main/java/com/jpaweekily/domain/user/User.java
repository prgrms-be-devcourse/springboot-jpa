package com.jpaweekily.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String nickName;

    private LocalDateTime createdAt;

    @Builder
    public User(String loginId, String nickName, String password, LocalDateTime createdAt) {
        this.loginId = loginId;
        this.nickName = nickName;
        this.password = password;
        this.createdAt = createdAt;
    }
}
