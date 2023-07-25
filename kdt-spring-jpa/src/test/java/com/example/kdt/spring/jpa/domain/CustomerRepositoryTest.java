package com.example.kdt.spring.jpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    @Order(1)
    @DisplayName("고객을 생성할 수 있다.")
    void createTest() {
        // Given
        Customer customer = new Customer(1L, "honggu", "honggu");

        // When
        repository.save(customer);

        // Then
        Customer entity = repository.findById(1L).get();
        log.info("{} {}", entity.getFirstName(), entity.getLastName());
    }

    @Test
    @Order(2)
    @DisplayName("고객을 업데이트 할 수 있다.")
    void updateTest() {
        // Given
        Customer customer = repository.findById(1L).get();

        // When
        customer.updateFullName("구", "범모");
        repository.save(customer);

        // Then
        Customer retrievedCustomer = repository.findById(1L).get();
        assertEquals("범모", retrievedCustomer.getLastName());
        assertEquals("구", retrievedCustomer.getFirstName());
    }

    @Test
    @Order(3)
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