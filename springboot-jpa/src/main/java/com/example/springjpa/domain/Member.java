package com.example.springjpa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="member")
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="name", nullable = false, length = 30)
    private String name;

    @Column(name="nick_name", nullable = false, length = 30, unique = true)
    private String nickName;

    private int age;

    @Column(name="address", nullable = false)
    private String address;

    @Column(name="description", nullable = false)
    private String description;
}
