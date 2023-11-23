package com.programmers.springbootjpa.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    private long id;

    private String firstName;

    private String lastName;

}
