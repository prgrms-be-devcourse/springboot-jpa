package com.kdt.kdtjpa.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class JPATest {

    @Autowired
    CustomerRepository repository;

    private Customer customer;

    @BeforeEach
    void setUp() {
        this.customer = Customer.createCustomer()
                .id(1L)
                .lastName("Hwang")
                .firstName("Junho")
                .build();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @DisplayName("Customer 객체를 저장하고 조회하면 같은 값을 보장한다.")
    @Test
    void insert_test() {
        // When
        repository.save(customer);

        // Then
        Customer entity = repository.findById(1L).orElseThrow();
        assertThat(customer).isEqualTo(entity);
    }

    @DisplayName("Customer 값이 없는데 조회하면 예외를 발생한다.")
    @Test
    void find_test() {
        // When, Then
        assertThatThrownBy(() -> repository.findById(1L).orElseThrow())
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("Customer 값을 update한 다음 조회하면 같은 객체를 보장한다.")
    @Test
    void update_test() {
        // Given
        repository.save(customer);

        // When
        Customer entity = repository.findById(1L).orElseThrow();
        entity.changeFirstName("Kim");
        entity.changeLastName("Happy");

        // Then
        Customer updated = repository.findById(1L).orElseThrow();
        assertThat(entity).isEqualTo(updated);
    }

    @DisplayName("Customer 객체를 저장, 삭제 후 조회 하면 예외가 발생한다.")
    @Test
    void delete_test() {
        // Given
        repository.save(customer);

        // When
        repository.delete(customer);

        // Then
        assertThatThrownBy(() -> repository.findById(1L).orElseThrow())
                .isInstanceOf(NoSuchElementException.class);
    }
}
