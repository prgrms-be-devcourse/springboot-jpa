package com.example.jpaweekly.repository;

import com.example.jpaweekly.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}