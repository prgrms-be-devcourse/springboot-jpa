package org.devcourse.springbootjpa.domain.customer.repository;

import org.devcourse.springbootjpa.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
