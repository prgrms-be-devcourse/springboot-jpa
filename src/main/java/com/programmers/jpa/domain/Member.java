package com.programmers.jpa.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public Member(String firstName, String lastName) {
        nullCheck(firstName, lastName);
        validateFirstName(firstName);
        validateLastName(lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private void nullCheck(String firstName, String lastName) {
        if (isNull(firstName)) {
            throw new IllegalArgumentException("First name is mandatory");
        }
        if (isNull(lastName)) {
            throw new IllegalArgumentException("Last name is mandatory");
        }
    }

    public void updateMember(String firstName, String lastName) {
        if (nonNull(firstName)) {
            validateFirstName(firstName);
            this.firstName = firstName;
        }
        if (nonNull(lastName)) {
            validateLastName(lastName);
            this.lastName = lastName;
        }
    }

    private void validateFirstName(String firstName) {
        if (invalidName(firstName)) {
            throw new IllegalArgumentException("Invalid first name");
        }
    }

    private void validateLastName(String lastName) {
        if (invalidName(lastName)) {
            throw new IllegalArgumentException("Invalid last Name");
        }
    }

    private boolean invalidName(String name) {
        boolean valid = NAME_PATTERN.matcher(name).matches();
        return !valid;
    }
}
