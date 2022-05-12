package com.example.springjpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@SequenceGenerator(
        name = "CUSTOMER_SEQ_GENERATOR",
        sequenceName = "CUSTOMER_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "CUSTOMER_SEQ_GENERATOR")
    private Long id;
    private String firstName;
    private String lastName;

    protected Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void changeFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void changeLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
