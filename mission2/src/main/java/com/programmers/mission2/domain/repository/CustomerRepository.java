package com.programmers.mission2.domain.repository;

import com.programmers.mission2.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
