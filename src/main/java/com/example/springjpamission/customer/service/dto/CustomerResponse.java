package com.example.springjpamission.customer.service.dto;

import com.example.springjpamission.customer.domain.Customer;

public record CustomerResponse(Long id, String firstName, String lastName) {

    public CustomerResponse(Customer customer) {
        this(customer.getId(), customer.getName().getFirstName(), customer.getName().getLastName());
    }

}
