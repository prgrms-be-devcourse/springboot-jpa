package com.example.mission1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Builder
    public Customer(String uuid, String email, String address, String name, int age) {
        this.uuid = uuid;
        this.email = email;
        this.address = address;
        this.name = name;
        this.age = age;
    }

    @Id
    @Column(name = "id")
    private String uuid;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 50)
    private String address;

    @Column(nullable = false)
    private String name;

    @Column
    private int age;

}
