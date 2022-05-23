package com.kdt.jpa.customer.repository;

import com.kdt.jpa.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
