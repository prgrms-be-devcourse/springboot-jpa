package com.prgrms.jpamission.customer.repository;

import com.prgrms.jpamission.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByFirstName(String firstName);
}
