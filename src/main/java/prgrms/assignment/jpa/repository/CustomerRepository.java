package prgrms.assignment.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prgrms.assignment.jpa.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
