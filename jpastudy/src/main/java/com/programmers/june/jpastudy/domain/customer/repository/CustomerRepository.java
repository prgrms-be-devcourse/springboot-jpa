package com.programmers.june.jpastudy.domain.customer.repository;

import com.programmers.june.jpastudy.domain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
