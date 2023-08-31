package com.example.weeklyjpa.repository;

import com.example.weeklyjpa.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
