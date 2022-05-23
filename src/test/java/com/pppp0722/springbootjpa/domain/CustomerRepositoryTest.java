package com.pppp0722.springbootjpa.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    Customer customer = new Customer();

    @BeforeEach
    void setUp() {
        customer.setId(1L);
        customer.setFirstName("ilhwan");
        customer.setLastName("lee");
        repository.save(customer);
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void insertTest() {
        Customer newCustomer = new Customer();
        newCustomer.setId(2L);
        newCustomer.setFirstName("ilhwan");
        newCustomer.setLastName("kim");

        repository.save(newCustomer);

        Customer foundCustomer = repository.findById(2L)
            .orElseThrow(() -> new EntityNotFoundException("해당 Customer 가 존재하지 않음."));

        assertAll(
            () -> assertEquals(foundCustomer.getId(), newCustomer.getId(), "두 Customer 의 id는 다름."),
            () -> assertEquals(foundCustomer.getFirstName(), newCustomer.getFirstName(),
                "두 Customer 의 firstName은 다름."),
            () -> assertEquals(foundCustomer.getLastName(), newCustomer.getLastName(),
                "두 Customer 의 lastName은 다름")
        );
    }

    @Test
    void findByIdTest() {
        Customer foundCustomer = repository.findById(1L)
            .orElseThrow(() -> new EntityNotFoundException("해당 Customer 가 존재하지 않음."));

        assertAll(
            () -> assertEquals(foundCustomer.getId(), customer.getId(), "두 Customer 의 id는 다름."),
            () -> assertEquals(foundCustomer.getFirstName(), customer.getFirstName(),
                "두 Customer 의 firstName은 다름."),
            () -> assertEquals(foundCustomer.getLastName(), customer.getLastName(),
                "두 Customer 의 lastName은 다름")
        );
    }

    @Test
    void findAllTest() {
        List<Customer> foundCustomers = repository.findAll();
        assertNotEquals(0, foundCustomers.size(), "Customer 가 존재하지 않음.");
    }

    @Test
    void updateTest() {
        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        customer.setFirstName("hwanil");
        customer.setLastName("kim");

        repository.save(customer);

        Customer updatedCustomer = repository.findById(1L)
            .orElseThrow(() -> new EntityNotFoundException("해당 Customer 가 존재하지 않음."));

        assertAll(
            () -> assertNotEquals(firstName, updatedCustomer.getFirstName(),
                "Customer 의 firstName 이 업데이트되지 않음."),
            () -> assertNotEquals(lastName, updatedCustomer.getLastName(),
                "Customer 의 lastName 이 업데이트되지 않음.")
        );
    }

    @Test
    void deleteByIdTest() {
        repository.deleteById(1L);
        List<Customer> foundCustomers = repository.findAll();
        assertEquals(0, foundCustomers.size());
    }

    @Test
    void deleteAllTest() {
        repository.deleteAll();
        List<Customer> foundCustomers = repository.findAll();
        assertEquals(0, foundCustomers.size());
    }
}