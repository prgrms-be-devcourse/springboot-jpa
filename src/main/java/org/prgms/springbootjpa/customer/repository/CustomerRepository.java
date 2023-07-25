package org.prgms.springbootjpa.customer.repository;

import org.prgms.springbootjpa.customer.domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
