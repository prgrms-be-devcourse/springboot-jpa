package com.programmers.springbootjpa.repository;

import com.programmers.springbootjpa.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
