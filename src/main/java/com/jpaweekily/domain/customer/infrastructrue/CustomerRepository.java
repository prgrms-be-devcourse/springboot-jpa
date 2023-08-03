package com.jpaweekily.domain.customer.infrastructrue;

import com.jpaweekily.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
