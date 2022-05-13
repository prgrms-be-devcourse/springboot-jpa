package com.kdt.jpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;


@Slf4j
@SpringBootTest
@DisplayName("customer CRUD 테스트")
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    Customer customer;

    @BeforeEach()
    void setup() {
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("beomseok");
        customer.setLastName("ko");
    }

    @AfterEach
    void reset() {
        repository.deleteAll();
    }


    @Test
    @DisplayName("삽입 테스트")
    void testCreate() {
        // Given

        // When
        repository.save(customer);

        // Then
        Customer entity = repository.findById(1L).get();
        assertThat(entity, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("조회 테스트")
    void testRead() {
        // Given

        // When
        repository.save(customer);

        // Then
        Customer entity = repository.findById(1L).get();
        assertThat(entity, samePropertyValuesAs(customer));
        List<Customer> customers = repository.findAll();
        assertThat(customers, hasSize(1));
    }

    @Test
    @DisplayName("수정 테스트")
    void testUpdate() {
        // Given
        repository.save(customer);
        customer.setLastName("go");

        // When
        repository.save(customer);

        // Then
        Customer entity = repository.findById(1L).get();
        assertThat(entity, samePropertyValuesAs(customer));
        assertThat(entity.getLastName().equals("go"), is(true));
        List<Customer> customers = repository.findAll();
        assertThat(customers, hasSize(1));
    }

    @Test
    @DisplayName("삭제 테스트")
    void testDelete() {
        // Given
        repository.save(customer);

        // When
        repository.deleteById(1L);

        // Then
        Optional<Customer> entity = repository.findById(1L);
        assertThat(entity.isEmpty(), is(true));
        List<Customer> customers = repository.findAll();
        assertThat(customers.isEmpty(), is(true));
    }
}