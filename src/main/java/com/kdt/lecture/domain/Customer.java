package com.kdt.lecture.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    public void updateCustomerName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
