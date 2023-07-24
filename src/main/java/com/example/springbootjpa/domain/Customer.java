package com.example.springbootjpa.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String address;

    public void updateCustomer(Customer customer) {
        this.username = customer.getUsername();
        this.address = customer.getAddress();
    }

    @Builder
    public Customer(Long id, String username, String address) {
        this.id = id;
        this.username = username;
        this.address = address;
    }
}
