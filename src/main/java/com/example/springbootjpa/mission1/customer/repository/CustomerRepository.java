package com.example.springbootjpa.mission1.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springbootjpa.mission1.customer.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
