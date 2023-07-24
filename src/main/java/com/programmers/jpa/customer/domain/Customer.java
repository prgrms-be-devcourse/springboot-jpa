package com.programmers.jpa.customer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Customer {

    protected Customer() {
    }

    @Id
    private Long id;

    @Column(length = 5, nullable = false)
    private String firstName;

    @Column(length = 2, nullable = false)
    private String lastName;
}
