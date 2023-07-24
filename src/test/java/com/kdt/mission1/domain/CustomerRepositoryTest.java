package com.kdt.mission1.domain;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerRepositoryTest {
    private final Logger log = LoggerFactory.getLogger(CustomerRepositoryTest.class);

    @Autowired
    private CustomerRepository repository;

    @Test
    public void testSave() {
        // Given
        Customer customer = new Customer(1L, "seongwon", "choi");
        // When
        repository.save(customer);
        // Then
        Customer persistCustomer = repository.findById(1L).get();
        log.info("FullName: {} {}", persistCustomer.getFirstName(), persistCustomer.getLastName());
    }
}