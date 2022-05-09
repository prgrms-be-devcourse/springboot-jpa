package com.prgrms.springJpa.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Customer {

    @Id
    private Long id;
    private String firstName;
    private String lastName;

}
