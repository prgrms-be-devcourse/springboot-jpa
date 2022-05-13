package com.example.jpademo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class Customer {
    @Id
    private long id;
    private String firstName;
    private String lastName;
}
