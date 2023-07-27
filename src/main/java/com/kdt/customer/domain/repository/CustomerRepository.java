package com.kdt.customer.domain.repository;

import com.kdt.customer.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository {
    Customer save(Customer customer);

    Optional<Customer> findById(Long id);

    void update(Long id, String firstName, String lastName);

    void deleteById(Long id);
}
