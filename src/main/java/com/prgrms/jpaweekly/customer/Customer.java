package com.prgrms.jpaweekly.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @Column(name = "id")
    private long id;
    private String firstName;
    private String lastName;
}
