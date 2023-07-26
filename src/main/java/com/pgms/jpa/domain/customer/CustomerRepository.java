package com.pgms.jpa.domain.customer;

import com.pgms.jpa.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
