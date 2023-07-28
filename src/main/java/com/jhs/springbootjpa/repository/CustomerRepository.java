package com.jhs.springbootjpa.repository;

import com.jhs.springbootjpa.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
