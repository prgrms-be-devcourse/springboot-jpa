package com.kdt.springbootjpa.customer.repository;

import com.kdt.springbootjpa.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
