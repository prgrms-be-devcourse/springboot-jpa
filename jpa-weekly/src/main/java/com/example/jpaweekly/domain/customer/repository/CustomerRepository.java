package com.example.jpaweekly.domain.customer.repository;

import com.example.jpaweekly.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    String findByFirstName(String firstName);

}
