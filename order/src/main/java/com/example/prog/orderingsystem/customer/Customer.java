package com.example.prog.orderingsystem.customer;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "customers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long customerId;
    private String firstName;
    private String lastName;

    private Customer(Long customerId, String firstName, String lastName) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Builder
    private Customer(String firstName, String lastName) {
        this(null, firstName, lastName);
    }

    public void rename(String firstName) {
        this.firstName = firstName;
    }
}
