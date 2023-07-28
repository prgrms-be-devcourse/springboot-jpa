package com.example.springbootjpa.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springbootjpa.customer.model.Customer;

public interface JpaCustomerRepository extends JpaRepository<Customer, Long> {
}
