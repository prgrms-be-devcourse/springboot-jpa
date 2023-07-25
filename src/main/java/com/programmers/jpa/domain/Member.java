package com.programmers.jpa.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(name = "name", nullable = false, length = 20, unique = true)
    private String username;
    @Column(name = "nickname", nullable = false, length = 20, unique = true)
    private String nickname;
    @Column(name = "address", nullable = false, length = 200)
    private String address;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Member() {
    }

    public Member(String username, String nickname, String address) {
        this.username = username;
        this.nickname = nickname;
        this.address = address;
        this.createdAt = LocalDateTime.now();
    }

    public void update(String nickname, String address) {
        if(nickname != null) {
            this.nickname = nickname;
        }
        if(address != null) {
            this.address = address;
        }
    }
}
