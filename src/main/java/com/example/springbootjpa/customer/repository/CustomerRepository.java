package com.example.springbootjpa.customer.repository;

import com.example.springbootjpa.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
