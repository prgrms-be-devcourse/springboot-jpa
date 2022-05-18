package com.programmers.jpa.domain;

import javax.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    private long id;

    @Embedded
    private Name name;

    public Customer() {}

    public Customer(long id, String firstName, String lastName) {
        this.id = id;
        this.name = new Name(firstName, lastName);
    }

    public long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public void changeName(Name name) {
        this.name = name;
    }
}
