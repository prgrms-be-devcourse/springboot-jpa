package com.will.jpapractice.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    void test() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("will");
        customer.setLastName("kim");

        // When
        repository.save(customer);

        // Then
        Customer entity = repository.findById(1L).get();
        log.info("{} {}", entity.getFirstName(), entity.getLastName());
    }
}