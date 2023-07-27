package com.programmers.jpa_mission.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "customers")
public class Customer {
    private static final String NAME_PATTERN = "^[가-힣a-zA-Z]+$";
    private static final String INVALID_NAME = "이름으로는 한글 또는 영어만 가능합니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    protected Customer() {
    }

    public Customer(String firstName, String lastName) {
        validateName(firstName);
        validateName(lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void update(String firstName) {
        this.firstName = firstName;
    }

    private void validateName(String name) {
        if (!Pattern.matches(NAME_PATTERN, name)) {
            throw new IllegalArgumentException(INVALID_NAME);
        }
    }
}
