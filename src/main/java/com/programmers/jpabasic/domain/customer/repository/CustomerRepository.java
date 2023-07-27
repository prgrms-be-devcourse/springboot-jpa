package com.programmers.jpabasic.domain.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.jpabasic.domain.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
