package com.kdt.kdtjpa.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter @Setter
@EqualsAndHashCode
public class Customer {
    @Id
    private long id;
    private String firstName;
    private String lastName;
}
