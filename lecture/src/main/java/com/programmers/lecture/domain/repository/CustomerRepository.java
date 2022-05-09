package com.programmers.lecture.domain.repository;

import com.programmers.lecture.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
