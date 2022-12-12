package kdt.springbootjpa.customer.repository;

import kdt.springbootjpa.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
