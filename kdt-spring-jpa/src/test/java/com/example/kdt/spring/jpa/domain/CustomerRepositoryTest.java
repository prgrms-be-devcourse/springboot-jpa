package com.example.kdt.spring.jpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    @DisplayName("고객을 생성할 수 있다.")
    void createTest() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("honggu");
        customer.setLastName("honggu");

        // When
        repository.save(customer);

        // Then
        Customer entity = repository.findById(1L).get();
        log.info("{} {}", entity.getFirstName(), entity.getLastName());
    }

    @Test
    @DisplayName("고객을 업데이트 할 수 있다.")
    void updateTest() {
        // Given
        Customer customer = repository.findById(1L).get();

        // When
        customer.setLastName("범모");
        repository.save(customer);

        // Then
        Customer retrievedCustomer = repository.findById(1L).get();
        assertEquals("범모", retrievedCustomer.getLastName());
        assertEquals("honggu", retrievedCustomer.getFirstName());
    }

    @Test
    @DisplayName("고객을 삭제할 수 있다.")
    void deleteTest() {
        // Given
        Customer customer = repository.findById(1L).get();

        // When
        repository.delete(customer);

        // Then
        Optional<Customer> retrievedCustomer = repository.findById(1L);
        assertTrue(retrievedCustomer.isEmpty());
    }

}