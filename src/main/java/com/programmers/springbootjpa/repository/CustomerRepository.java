package com.programmers.springbootjpa.repository;

import com.programmers.springbootjpa.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
