package com.prgrms.jpa.jpa1.repository;

import com.prgrms.jpa.jpa1.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
