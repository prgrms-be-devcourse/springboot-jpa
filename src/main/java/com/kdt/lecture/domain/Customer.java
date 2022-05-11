package com.kdt.lecture.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    private long id;
    private String firstName;
    private String lastName;

    public void changeFirstName(String newFirstName) {
        this.firstName = newFirstName;
    }
}
