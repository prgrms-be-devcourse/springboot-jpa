package com.springbootjpa.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Customer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Embedded
    private Name name;

    public Customer(String firstName, String lastName) {
        this.name = new Name(firstName, lastName);
    }

    public void changeName(String firstName, String lastName) {
        this.name = new Name(firstName, lastName);
    }
}
