package com.jhs.springbootjpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;

    protected Customer() {
    }

    public Customer(Long id, String firstName, String lastName) {
        validateName(firstName);
        validateName(lastName);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private void validateName(String name) {
        validateNameNotEmpty(name);
        validatePattern(name);
    }

    private void validateNameNotEmpty(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("이름은 빈 값일 수 없습니다");
        }
    }

    private void validatePattern(String name) {
        String namePattern = "^[a-zA-Zㄱ-ㅎ가-힣]*$";
        if (!Pattern.matches(namePattern, name)) {
            throw new IllegalArgumentException("한글과 영어만 사용가능합니다");
        }
    }

    public void updateFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }
}
