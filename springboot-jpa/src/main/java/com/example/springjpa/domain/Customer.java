package com.example.springjpa.domain;

import com.example.springjpa.domain.order.vo.Name;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@SequenceGenerator(
        name = "CUSTOMER_SEQ_GENERATOR",
        sequenceName = "CUSTOMER_SEQ",
        allocationSize = 1
)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "CUSTOMER_SEQ_GENERATOR")
    private Long id;
    @Embedded
    private Name name;

    protected Customer() {
    }

    public Customer(Name name) {
        this.name = name;
    }

    public void changeName(Name name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }
}
