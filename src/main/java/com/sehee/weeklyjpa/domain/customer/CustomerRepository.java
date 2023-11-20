package com.sehee.weeklyjpa.domain.customer;

import com.sehee.weeklyjpa.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
