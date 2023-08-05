package com.programmers.jpa.customer.domain;

import com.programmers.jpa.base.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.Objects;

@Entity
@Getter
public class Customer extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 5, nullable = false)
    private String firstName;

    @Column(length = 2, nullable = false)
    private String lastName;

    protected Customer() {
    }

    public Customer(String firstName, String lastName) {
        validateFirstName(firstName);
        validateLastName(lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void changeName(String firstName, String lastName) {
        validateFirstName(firstName);
        validateLastName(lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private static void validateLastName(String lastName) {
        if (Objects.isNull(lastName) || lastName.isBlank()) {
            throw new IllegalArgumentException("성이 비어있습니다.");
        }
    }

    private static void validateFirstName(String firstName) {
        if (Objects.isNull(firstName) || firstName.isBlank()) {
            throw new IllegalArgumentException("이름이 비어있습니다.");
        }
    }
}
