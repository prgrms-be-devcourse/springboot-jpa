package com.kdt.jpaproject.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@Getter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstName;
    private String lastName;

    public Customer() {
    }
}
