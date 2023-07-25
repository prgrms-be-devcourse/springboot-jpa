package com.programmers.week.customer.application;

import com.programmers.week.customer.ui.CustomerCreateRequest;
import com.programmers.week.customer.domain.Customer;
import com.programmers.week.customer.infra.CustomerRepository;
import com.programmers.week.customer.ui.CustomerResponse;
import com.programmers.week.customer.ui.CustomerUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;

  @Transactional
  public Long create(CustomerCreateRequest createRequest) {
    Customer customer = new Customer(
            createRequest.firstName(),
            createRequest.lastName()
    );
    Customer saved = customerRepository.save(customer);
    return saved.getId();
  }

  public CustomerResponse findById(Long id) {
    return customerRepository.findById(id)
            .map(CustomerResponse::from)
            .orElseThrow(() -> new IllegalArgumentException("고객이 존재하지 않습니다."));
  }

  public List<CustomerResponse> findAll() {
    List<Customer> customers = customerRepository.findAll();
    return customers.stream()
            .map(CustomerResponse::from)
            .toList();
  }

  @Transactional
  public Long update(CustomerUpdateRequest customerUpdateRequest) {
    Customer customer = customerRepository.findById(customerUpdateRequest.id())
            .orElseThrow(() -> new IllegalArgumentException("고객이 존재하지 않습니다."));
    customer.changeFirstName(customerUpdateRequest.firstName());
    customer.changeLastName(customerUpdateRequest.lastName());
    return customer.getId();
  }

  @Transactional
  public void deleteById(Long id) {
    customerRepository.deleteById(id);
  }

}
