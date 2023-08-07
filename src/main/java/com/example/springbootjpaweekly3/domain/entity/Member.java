package com.example.springbootjpaweekly3.domain.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "members")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name", nullable = false)
    private String name;

    @Column(name = "nick_name")
    private String nickName;

    private int age;
    @Column(nullable = false)
    private String address;

    private String description;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Member(Long id, String name, String nickName, int age, String address, String description) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
    }
}
