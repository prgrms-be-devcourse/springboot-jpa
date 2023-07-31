package com.example.springjpamission.customer.service;

import com.example.springjpamission.customer.domain.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService (CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Transactional
    public SaveCustomerRespone save(SaveCustomerRequest saveCustomerRequest){
        customerRepository.
    }
}
