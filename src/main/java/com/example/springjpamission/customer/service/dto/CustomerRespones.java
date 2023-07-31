package com.example.springjpamission.customer.service.dto;

import com.example.springjpamission.customer.domain.Customer;

import java.util.List;
import java.util.stream.Collectors;

public record CustomerRespones(List<CustomerRespone> customerRespones) {

    public static CustomerRespones of(List<Customer> customers) {
        List<CustomerRespone> responses = customers.stream().map(CustomerRespone::new)
                .collect(Collectors.toList());
        return new CustomerRespones(responses);
    }

}
