package com.example.springbootjpaweekly.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Table(name = "customers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {
    private static final String NAME_PATTERN = "^[a-zA-Z가-힣]+$";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "first_name")
    @Pattern(regexp = NAME_PATTERN, message = "성은 알파벳 또는 한글만 가능합니다")
    private String firstName;

    @Column(name = "last_name")
    @Pattern(regexp = NAME_PATTERN, message = "이름은 알파벳 또는 한글만 가능합니다")
    private String lastName;

    @Builder
    public Customer(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
