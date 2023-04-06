package com.programmers.kwonjoosung.springbootjpa.model;

import lombok.AccessLevel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 20) @Comment("이름")
    @NotBlank
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20) @Comment("성")
    @NotBlank
    private String lastName;

    public Customer(String firstName, String lastName) {
        validFirstName(firstName);
        validLastName(lastName);
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }

    public void changeFirstName(String firstName) {
        validFirstName(firstName);
        this.firstName = firstName.trim();
    }

    public void changeLastName(String lastName) {
        validLastName(lastName);
        this.lastName = lastName.trim();
    }

    private void validFirstName(String firstName) {
        checkLength(firstName);
        checkText(firstName);
    }

    private void validLastName(String lastName) {
        checkLength(lastName);
        checkText(lastName);
    }

    private void checkLength(String name) {
        Assert.isTrue(name.trim().length() <= 20, "20자 이하로 입력해주세요");
    }

    private void checkText(String name) {
        Assert.isTrue(name.matches("^[a-zA-Z가-힣]+$"), "한글, 영어만 허용합니다");
    }

}
