package com.programmers.kwonjoosung.springbootjpa.repository;

import com.programmers.kwonjoosung.springbootjpa.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
