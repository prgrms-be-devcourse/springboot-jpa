package com.programmers.jpa.customer.application;

import com.programmers.jpa.customer.domain.Customer;
import com.programmers.jpa.customer.infra.CustomerRepository;
import com.programmers.jpa.customer.ui.CreateRequest;
import com.programmers.jpa.customer.ui.FindResponse;
import com.programmers.jpa.customer.ui.UpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Long create(CreateRequest createRequest) {
        Customer customer = new Customer(createRequest.firstName(), createRequest.firstName());
        customerRepository.save(customer);
        return customer.getId();
    }

    public FindResponse findById(Long id) {
        return customerRepository.findById(id)
                .map(FindResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("고객이 존재하지 않습니다."));
    }

    public List<FindResponse> findAll() {
        return customerRepository
                .findAll()
                .stream()
                .map(FindResponse::from)
                .toList();
    }

    @Transactional
    public Long update(UpdateRequest updateRequest) {
        Customer foundCustomer = customerRepository.findById(updateRequest.id())
                .orElseThrow(() -> new IllegalArgumentException("고객이 존재하지 않습니다."));
        foundCustomer.changeFirstName(updateRequest.firstName());
        foundCustomer.changeLastName(updateRequest.lastName());
        return foundCustomer.getId();
    }

    @Transactional
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}
