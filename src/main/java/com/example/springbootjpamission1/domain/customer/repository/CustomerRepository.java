package com.example.springbootjpamission1.domain.customer.repository;

import com.example.springbootjpamission1.domain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
