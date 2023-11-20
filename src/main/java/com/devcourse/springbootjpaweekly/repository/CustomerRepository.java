package com.devcourse.springbootjpaweekly.repository;

import com.devcourse.springbootjpaweekly.domain.Customer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

}
