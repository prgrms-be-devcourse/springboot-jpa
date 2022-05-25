package com.prgrms.springbootjpa.domain.customer;

import com.prgrms.springbootjpa.global.util.EntityFieldValidator;

import javax.persistence.*;

@Entity
@Table(name="customers")
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    protected Customer() {
    }

    public Customer(String firstName, String lastName) {
        EntityFieldValidator.validateCustomerField(firstName, lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void changeFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void changeLastName(String lastName) {
        this.lastName = lastName;
    }
}
