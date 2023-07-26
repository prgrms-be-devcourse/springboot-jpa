package com.kdt.weeklyjpa.domain.customer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customer_id;

    @Column(name = "first_name", nullable = false, length = 20)
    @NotBlank
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    @NotBlank
    private String lastName;

    @Column(name = "phone", nullable = false, length = 20, unique = true)
    @NotBlank
    private String phone;

    public Customer(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public void updateFirstName(String firstName) {
        this.firstName = firstName;
    }
}
