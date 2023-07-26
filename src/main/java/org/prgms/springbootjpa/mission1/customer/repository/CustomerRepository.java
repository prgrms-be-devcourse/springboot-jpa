package org.prgms.springbootjpa.mission1.customer.repository;

import org.prgms.springbootjpa.mission1.customer.domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
