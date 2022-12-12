package com.devcourse.mission.domain.customer.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Table(name = "customers")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {
    @Id
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column( nullable = false, length = 30)
    private String address;

    @Column(nullable = false)
    private int age;

    @Builder
    public Customer(Long id, String name, String address, int age) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public void changeAge(int age) {
        this.age = age;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changeName(String name) {
        this.name = name;
    }
}
