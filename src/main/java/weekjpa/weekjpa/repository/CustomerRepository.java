package weekjpa.weekjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import weekjpa.weekjpa.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
