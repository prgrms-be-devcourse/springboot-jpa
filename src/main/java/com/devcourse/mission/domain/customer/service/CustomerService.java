package com.devcourse.mission.domain.customer.service;

import com.devcourse.mission.domain.customer.entity.Customer;
import com.devcourse.mission.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public long save(String name, String address, int age) {
        Customer customer = new Customer(name, address, age);
        return customerRepository.save(customer).getId();
    }

    public Customer getById(long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 회원 번호 입니다."));
    }
}
