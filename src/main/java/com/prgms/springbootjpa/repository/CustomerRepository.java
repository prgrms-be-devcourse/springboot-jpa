package com.prgms.springbootjpa.repository;

import com.prgms.springbootjpa.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
