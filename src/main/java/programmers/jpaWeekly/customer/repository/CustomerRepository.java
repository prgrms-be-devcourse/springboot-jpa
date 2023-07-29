package programmers.jpaWeekly.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programmers.jpaWeekly.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
