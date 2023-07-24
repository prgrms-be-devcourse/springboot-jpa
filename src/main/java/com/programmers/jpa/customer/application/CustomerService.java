package com.programmers.jpa.customer.application;

import com.programmers.jpa.customer.domain.Customer;
import com.programmers.jpa.customer.infra.CustomerRepository;
import com.programmers.jpa.customer.ui.CreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Long create(CreateRequest createRequest) {
        Customer customer = new Customer(createRequest.firstName(), createRequest.firstName());
        customerRepository.save(customer);
        return customer.getId();
    }
}
