package com.example.jpalecture.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@Table(name = "customers")
@Entity
public class Customer {

    @Id
    private long id;

    private String firstName;

    private String lastName;
}

