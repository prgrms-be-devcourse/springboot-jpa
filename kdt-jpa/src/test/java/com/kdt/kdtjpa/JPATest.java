package com.kdt.kdtjpa;

import com.kdt.kdtjpa.domain.Customer;
import com.kdt.kdtjpa.domain.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
@Transactional
public class JPATest {

    @Autowired
    CustomerRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void insert_test() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("junho");
        customer.setLastName("hwang");

        // When
        repository.save(customer);

        // Then
        Customer entity = repository.findById(1L).orElseThrow();
        assertThat(customer).isEqualTo(entity);
    }

    @Test
    void find_test() {
        // When, Then
        assertThatThrownBy(() -> repository.findById(1L).orElseThrow())
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void update_test() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("junho");
        customer.setLastName("hwang");
        repository.save(customer);

        // When
        Customer entity = repository.findById(1L).orElseThrow();
        entity.setFirstName("backend");
        entity.setLastName("programmers");

        // Then
        Customer updated = repository.findById(1L).orElseThrow();
        assertThat(entity).isEqualTo(updated);
    }

    @Test
    void delete_test() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("junho");
        customer.setLastName("hwang");
        repository.save(customer);

        // When
        repository.delete(customer);

        // Then
        assertThatThrownBy(() -> repository.findById(1L).orElseThrow())
                .isInstanceOf(NoSuchElementException.class);
    }
}
