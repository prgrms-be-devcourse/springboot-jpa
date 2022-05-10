package com.part4.jpa2.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // H2 전략
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 30, unique = true)
    private String nickName;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String address;

    @Lob
    private String description;

    @Builder
    public Member(String name, String nickName, int age, String address, String description) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.address = address;
        this.description = description;
    }
}
