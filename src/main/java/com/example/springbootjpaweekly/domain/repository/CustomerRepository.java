package com.example.springbootjpaweekly.domain.repository;

import com.example.springbootjpaweekly.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
