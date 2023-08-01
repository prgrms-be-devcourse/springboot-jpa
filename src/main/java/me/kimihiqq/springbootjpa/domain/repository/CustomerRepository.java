package me.kimihiqq.springbootjpa.domain.repository;

import me.kimihiqq.springbootjpa.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

