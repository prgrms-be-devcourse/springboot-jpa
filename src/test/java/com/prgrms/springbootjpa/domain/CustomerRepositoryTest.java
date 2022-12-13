package com.prgrms.springbootjpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
//@SpringBootTest
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("고객을 저장할 수 있다.")
    void testInsert() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setLastName("Kim");
        customer.setFirstName("Changgyu");

        // When
        Customer entity = customerRepository.save(customer);

        // Then
        assertThat(customer, samePropertyValuesAs(entity));
    }

    @Test
    @DisplayName("고객을 조회할 수 있다.")
    void testFind() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setLastName("Kim");
        customer.setFirstName("Changgyu");
        customerRepository.save(customer);

        // When
        Customer entity = customerRepository.findById(1L).orElseThrow();

        // Then
        assertThat(customer, samePropertyValuesAs(entity));
    }

    @Test
    @DisplayName("고객을 수정할 수 있다.")
    void testUpdate() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setLastName("Kim");
        customer.setFirstName("Changgyu");
        customerRepository.save(customer);

        // When
        Customer entity = customerRepository.findById(1L).orElseThrow();
        entity.setLastName("Park");
        customerRepository.save(entity);
        Customer updated = customerRepository.findById(1L).orElseThrow();

        // Then
        assertThat(entity, samePropertyValuesAs(updated));
    }

    @Test
    @DisplayName("고객을 삭제할 수 있다.")
    void testDelete() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setLastName("Kim");
        customer.setFirstName("Changgyu");
        customerRepository.save(customer);

        // When
        customerRepository.delete(customer);

        // Then
        assertThat(customerRepository.findById(1L), is(Optional.empty()));
    }
}