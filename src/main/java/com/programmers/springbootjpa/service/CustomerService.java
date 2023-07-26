package com.programmers.springbootjpa.service;

import com.programmers.springbootjpa.domain.Customer;
import com.programmers.springbootjpa.dto.CustomerCreateRequest;
import com.programmers.springbootjpa.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer createCustomer(CustomerCreateRequest request) {
        Customer savedCustomer = customerRepository.save(Customer.of(request));

        return savedCustomer;
    }
}
