package com.example.springjpamission.customer.service.dto;

import com.example.springjpamission.customer.domain.Customer;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

public record CustomerResponses(List<CustomerResponse> customerResponses) {

    public static CustomerResponses of(Slice<Customer> customers) {
        List<CustomerResponse> responses = customers.stream()
                                                    .map(CustomerResponse::of)
                                                    .collect(Collectors.toList());
        return new CustomerResponses(responses);
    }

}
