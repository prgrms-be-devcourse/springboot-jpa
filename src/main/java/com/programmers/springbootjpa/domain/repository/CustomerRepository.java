package com.programmers.springbootjpa.domain.repository;

import com.programmers.springbootjpa.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
