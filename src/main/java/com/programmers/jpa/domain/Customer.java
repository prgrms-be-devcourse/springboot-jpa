package com.programmers.jpa.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Customer {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    private Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static Customer of(String firstName, String lastName) {
        return new Customer(firstName, lastName);
    }

    public void changeName(String firstName, String lastName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }
}
