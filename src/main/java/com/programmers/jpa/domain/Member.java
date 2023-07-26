package com.programmers.jpa.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
@Entity
public class Member {
    private static final Pattern NAME_PATTERN = Pattern.compile("[A-Za-z]{1,20}$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    public Member() {
    }

    public Member(String firstName, String lastName) {
        validateFirstName(firstName);
        validateLastName(lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void update(String firstName, String lastName) {
        if(firstName != null) {
            validateFirstName(firstName);
            this.firstName = firstName;
        }
        if(lastName != null) {
            validateLastName(lastName);
            this.lastName = lastName;
        }
    }

    private void validateFirstName(String firstName) {
        if(invalidName(firstName)) {
            throw new IllegalArgumentException("Invalid first name");
        }
    }

    private void validateLastName(String lastName) {
        if(invalidName(lastName)) {
            throw new IllegalArgumentException("Invalid last Name");
        }
    }

    private boolean invalidName(String name) {
        boolean valid = NAME_PATTERN.matcher(name).matches();
        return !valid;
    }
}
