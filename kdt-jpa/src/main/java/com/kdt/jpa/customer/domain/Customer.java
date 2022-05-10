package com.kdt.jpa.customer.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter @Setter
public class Customer {
    @Id
    private long id;
    private String firstName;
    private String lastName;
}
