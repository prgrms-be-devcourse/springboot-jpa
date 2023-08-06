package com.jpaweekly.domain.user;

import com.jpaweekly.common.BaseEntity;
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
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

    private LocalDateTime createdAt;

    @Builder
    private User(String loginId, String nickname, String password, LocalDateTime createdAt) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.password = password;
        this.createdAt = createdAt;
    }
}
