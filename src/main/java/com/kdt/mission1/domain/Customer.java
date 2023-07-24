package com.kdt.mission1.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class Customer {
    @Id
    private long id;
    private String firstName;
    private String lastName;

    public Customer(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer() {
        this.id = 0L;
        this.firstName = "firstname";
        this.lastName = "lastname";
    }
}
