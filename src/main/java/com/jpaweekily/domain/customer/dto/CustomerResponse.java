package com.jpaweekily.domain.customer.dto;

import com.jpaweekily.domain.customer.Customer;

public record CustomerResponse (
        Long id,
        String firstName,
        String lastName
){
    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getFirstName(), customer.getLastName());
    }
}
