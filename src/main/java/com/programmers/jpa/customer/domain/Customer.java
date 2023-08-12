package com.programmers.jpa.customer.domain;

import com.programmers.jpa.base.domain.BaseEntity;
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
public class Customer extends BaseEntity {

    private static final int MAX_LAST_NAME_LENGTH = 2;
    private static final int MAX_FIRST_NAME_LENGTH = 5;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = MAX_FIRST_NAME_LENGTH, nullable = false)
    private String firstName;

    @Column(length = MAX_LAST_NAME_LENGTH, nullable = false)
    private String lastName;

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
        if (lastName.length() > MAX_LAST_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format("입력한 성이 %s 글자수를 넘었습니다.", MAX_LAST_NAME_LENGTH));
        }
    }

    private static void validateFirstName(String firstName) {
        if (Objects.isNull(firstName) || firstName.isBlank()) {
            throw new IllegalArgumentException("이름이 비어있습니다.");
        }
        if (firstName.length() > MAX_FIRST_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format("입력한 이름이 %s 글자수를 넘었습니다.", MAX_FIRST_NAME_LENGTH));
        }
    }
}
