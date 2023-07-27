package com.jpaweekily.domain.customer.repository;

import com.jpaweekily.domain.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByFirstName(String firstname);

    Page<Customer> findAll(Pageable pageable);
}
