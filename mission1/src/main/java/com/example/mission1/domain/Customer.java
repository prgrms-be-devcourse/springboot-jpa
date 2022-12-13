package com.example.mission1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Entity
@Table(name = "customer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    public Customer(String uuid, String name, String email, String address) {
        this.uuid = uuid;
        this.email = email;
        this.address = address;
        this.name = name;
    }

    public Customer(String uuid, String email, String address, String name, int age) {
        this.uuid = uuid;
        this.email = email;
        this.address = address;
        this.name = name;
        this.age = age;
    }

    @Id
    @Column(name = "id")
    @NotNull
    private String uuid;

    @Column(unique = true)
    @NotNull
    private String email;

    @Column(length = 50)
    @NotNull
    private String address;

    @NotNull
    private String name;

    @Column
    private int age;

    public void changeAddress(String address) {
        this.address = address;
    }
}
