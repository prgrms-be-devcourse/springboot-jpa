package com.programmers.springbootjpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Embedded
    private Address address;

    @Builder
    public Customer(String name, Integer age, String nickName, Address address) {
        this.name = name;
        this.age = age;
        this.nickName = nickName;
        this.address = address;
    }
}
