package com.devcourse.springbootjpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.springbootjpa.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
