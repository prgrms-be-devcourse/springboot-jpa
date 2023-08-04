package org.programmers.jpaweeklymission.customer.Infra;

import org.programmers.jpaweeklymission.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<Customer, Long> {
}
