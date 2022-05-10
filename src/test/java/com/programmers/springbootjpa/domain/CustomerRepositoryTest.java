package com.programmers.springbootjpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @AfterEach
    void cleanUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("고객 정보 생성")
    void insertTest() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("hyeonseo");
        customer.setLastName("JUNG");

        // When
        repository.save(customer);

        // Then
        Optional<Customer> entity = repository.findById(1L);
        assertThat(entity.isEmpty(), is(false));
        assertThat(entity.get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("고객 정보 조회")
    void findTest() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("hyeonseo");
        customer.setLastName("JUNG");

        repository.save(customer);

        // When
        Optional<Customer> entity = repository.findById(1L);

        // Then
        assertThat(entity.isEmpty(), is(false));
        assertThat(entity.get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("고객 정보 수정")
    void updateTest() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("hyeonseo");
        customer.setLastName("JUNG");

        repository.save(customer);

        // When
        Optional<Customer> optionalCustomer = repository.findById(1L);
        optionalCustomer.ifPresent(entity -> {
            entity.setFirstName("updatedName");
            entity.setLastName("Kim");
        });


        // Then
        assertThat(optionalCustomer.isEmpty(), is(false));
        assertThat(optionalCustomer.get().getFirstName(), is("updatedName"));
        assertThat(optionalCustomer.get().getLastName(), is("Kim"));
    }

    @Test
    @DisplayName("고객 정보 삭제")
    void deleteTest() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("hyeonseo");
        customer.setLastName("JUNG");

        repository.save(customer);

        // When
        repository.deleteById(1L);

        // Then
        List<Customer> customers = repository.findAll();
        assertThat(customers.isEmpty(), is(true));
    }
}