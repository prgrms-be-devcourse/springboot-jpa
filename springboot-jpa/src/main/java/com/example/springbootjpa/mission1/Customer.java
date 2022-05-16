package com.example.springbootjpa.mission1;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    private Long id;

    private String firstName;
    private String lastName;
}
