package com.kdt.mission1.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
class CustomerRepositoryTest {

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
        assertThat(customer, samePropertyValuesAs(persistCustomer));
    }
}