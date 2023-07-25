package com.kdt.lecture.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
class CustomerRepositoryTest {

    static Customer customer;

    @Autowired
    private CustomerRepository repository;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .firstName("SeonHeui")
                .lastName("Jeon")
                .build();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @DisplayName("Customer Create Test")
    @Test
    void testCreateCustomer() {
        repository.save(customer);

        assertNotNull(customer.getId());
        assertEquals(customer.getFirstName(), "SeonHeui");
        assertEquals(customer.getLastName(), "Jeon");
    }

    @DisplayName("Customer Read Test")
    @Test
    void testReadCustomer() {
        repository.save(customer);
        Customer readCustomer = repository.findById(customer.getId()).get();

        assertEquals(customer, readCustomer);
    }

    @DisplayName("Customer Update Test")
    @Test
    void testUpdateCustomer() {
        repository.save(customer);
        Customer readCustomer = repository.findById(customer.getId()).get();

        readCustomer.updateCustomerName("mingky", "Jeon");

        assertEquals(customer.getFirstName(), readCustomer.getFirstName());
        assertEquals(customer.getLastName(), readCustomer.getLastName());
    }

    @DisplayName("Customer Delete All Test")
    @Test
    void testDeleteAllCustomer() {
        repository.save(customer);

        repository.deleteAll();

        assertEquals(repository.count(), 0);
    }

    @DisplayName("Customer Delete Test")
    @Test
    void testDeleteCustomer() {
        repository.save(customer);

        repository.deleteById(customer.getId());

        Customer readCustomer = repository.findById(customer.getId()).orElse(null);
        assertNull(readCustomer);
    }
}