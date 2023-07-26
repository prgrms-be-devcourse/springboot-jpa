package com.programmers.springbootjpa.service;

import com.programmers.springbootjpa.domain.Customer;
import com.programmers.springbootjpa.dto.CustomerCreateRequest;
import com.programmers.springbootjpa.dto.CustomerResponse;
import com.programmers.springbootjpa.repository.CustomerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponse createCustomer(CustomerCreateRequest request) {
        Customer savedCustomer = customerRepository.save(Customer.of(request));

        return CustomerResponse.of(savedCustomer);
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerResponse::of)
                .toList();
    }
}
