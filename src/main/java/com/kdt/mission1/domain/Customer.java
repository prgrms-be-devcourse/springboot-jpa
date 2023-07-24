package com.kdt.mission1.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {
    @Id
    private long id;
    private String firstName;
    private String lastName;
}
