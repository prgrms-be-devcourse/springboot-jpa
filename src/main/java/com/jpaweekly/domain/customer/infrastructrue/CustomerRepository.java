package com.jpaweekly.domain.customer.infrastructrue;

import com.jpaweekly.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
