package com.programmers.jpamission1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.jpamission1.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
