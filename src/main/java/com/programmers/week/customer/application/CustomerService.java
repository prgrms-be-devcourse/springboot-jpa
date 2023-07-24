package com.programmers.week.customer.application;

import com.programmers.week.customer.ui.CustomerCreateRequest;
import com.programmers.week.customer.domain.Customer;
import com.programmers.week.customer.infra.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;

  public Long create(CustomerCreateRequest createRequest) {
    Customer customer = new Customer(
            createRequest.firstName(),
            createRequest.lastName()
    );
    Customer saved = customerRepository.save(customer);
    return saved.getId();
  }

}
