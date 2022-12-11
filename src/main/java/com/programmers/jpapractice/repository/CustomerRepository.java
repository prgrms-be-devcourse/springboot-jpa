package com.programmers.jpapractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.jpapractice.repository.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
