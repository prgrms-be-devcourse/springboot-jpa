package com.programmers.springbootjpa.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "customers")
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String firstName;

    @Column(nullable = false, length = 20)
    private String lastName;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Builder
    public Customer(Long id, String firstName, String lastName) {
        checkName(firstName);
        checkName(lastName);

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private void checkName(String name) {
        checkLength(name);
        checkPattern(name);
    }

    private void checkLength(String name) {
        if (name.isEmpty() || name.length() > 20) {
            throw new IllegalArgumentException("이름은 1자 이상 20자 이하로 입력해주세요.");
        }
    }

    private void checkPattern(String name) {
        if (!Pattern.matches("^[가-힣a-zA-Z]*$", name)) {
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
