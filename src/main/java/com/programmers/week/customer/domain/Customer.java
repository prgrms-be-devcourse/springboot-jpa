package com.programmers.week.customer.domain;

import com.programmers.week.exception.Message;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    private static final int MAX_FIRST_NAME_LENGTH = 5;
    private static final int MAX_LAST_NAME_LENGTH = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5, nullable = false)
    private String firstName;

    @Column(length = 2, nullable = false)
    private String lastName;

    public Customer(String firstName, String lastName) {
        validateFirstName(firstName);
        validateLastName(lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private static void validateFirstName(String firstName) {
        if (Objects.isNull(firstName) || firstName.isBlank()) {
            throw new IllegalArgumentException(Message.FIRSTNAME_IS_NULL);
        }
        if (firstName.length() > MAX_FIRST_NAME_LENGTH) {
            throw new IllegalArgumentException(Message.NAME_LENGTH_IS_WRONG);
        }
    }

    private static void validateLastName(String lastName) {
        if (Objects.isNull(lastName) || lastName.isBlank()) {
            throw new IllegalArgumentException(Message.LASTNAME_IS_NULL);
        }
        if (lastName.length() > MAX_LAST_NAME_LENGTH) {
            throw new IllegalArgumentException(Message.NAME_LENGTH_IS_WRONG);
        }
    }

    public void changeName(String firstName, String lastName) {
        validateFirstName(firstName);
        validateLastName(lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
