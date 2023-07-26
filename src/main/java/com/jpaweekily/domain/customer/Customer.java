package com.jpaweekily.domain.customer;

import com.jpaweekily.domain.customer.dto.CustomerResponse;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_Name", nullable = false)
    private String firstName;

    private String lastName;

    @Builder
    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void changeFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void changeLastName(String lastName) {
        this.lastName = lastName;
    }
}
