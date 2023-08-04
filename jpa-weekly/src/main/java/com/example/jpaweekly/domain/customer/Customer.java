package com.example.jpaweekly.domain.customer;

import com.example.jpaweekly.domain.customer.dto.CustomerResponse;
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

    @Column(name="first_name",nullable = false, unique = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Builder
    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static CustomerResponse from(Customer customer){
        return new CustomerResponse(customer.getFirstName(), customer.getLastName());
    }

    public void changeName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
