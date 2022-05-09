package com.jpa.springboot.domain;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
