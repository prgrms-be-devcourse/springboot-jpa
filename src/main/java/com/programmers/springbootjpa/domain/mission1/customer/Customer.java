package com.programmers.springbootjpa.domain.mission1.customer;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "customers")
@Entity
public class Customer {

    private static final int MAXIMUM_LENGTH_LIMIT = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 20)
    private String firstName;

    @Column(nullable = false, length = 20)
    private String lastName;

    public Customer(String firstName, String lastName) {
        checkName(firstName);
        checkName(lastName);

        this.firstName = firstName;
        this.lastName = lastName;
    }

    private void checkName(String name) {
        checkLength(name);
        checkPattern(name);
    }

    private void checkLength(String name) {
        if (name == null || name.isEmpty() || name.length() > MAXIMUM_LENGTH_LIMIT) {
            throw new IllegalArgumentException("이름은 1자 이상 20자 이하로 입력해주세요.");
        }
    }

    private void checkPattern(String name) {
        if (!Pattern.matches("^[가-힣a-zA-Z]+$", name)) {
            throw new IllegalArgumentException("이름은 한글 또는 영어로 입력해주세요.");
        }
    }

    public void updateFirstName(String firstName) {
        checkName(firstName);
        this.firstName = firstName;
    }

    public void updateLastName(String lastName) {
        checkName(lastName);
        this.lastName = lastName;
    }
}
