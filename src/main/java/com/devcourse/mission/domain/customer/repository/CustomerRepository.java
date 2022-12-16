package com.devcourse.mission.domain.customer.repository;

import com.devcourse.mission.domain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
