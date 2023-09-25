package com.example.kdt.spring.jpa.domain.order;

import com.example.kdt.spring.jpa.domain.Customer;
import com.example.kdt.spring.jpa.domain.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    private Customer customer;

    @BeforeEach
    void setup() {
        customer = new Customer("honggu", "honggu");
        repository.save(customer);
    }

    @AfterEach
    void clearup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("고객을 생성할 수 있다.")
    void createTest() {
        // Given

        // When

        // Then
        Customer retrievedCustomer = repository.findById(customer.getId()).get();
        assertThat(customer).usingRecursiveComparison().isEqualTo(retrievedCustomer);
    }

    @Test
    @DisplayName("고객을 업데이트 할 수 있다.")
    void updateTest() {
        // Given
        Customer retrievedCustomer = repository.findById(customer.getId()).get();

        // When
        retrievedCustomer.updateFullName("구", "범모");

        // Then
        Customer updatedCustomer = repository.findById(customer.getId()).get();
        assertEquals("범모", updatedCustomer.getLastName());
        assertEquals("구", updatedCustomer.getFirstName());
    }

    @Test
    @DisplayName("고객을 삭제할 수 있다.")
    void deleteTest() {
        // Given
        Long customerId = customer.getId();

        // When
        repository.delete(customer);

        // Then
        Optional<Customer> retrievedCustomer = repository.findById(customerId);
        assertTrue(retrievedCustomer.isEmpty());
    }

}