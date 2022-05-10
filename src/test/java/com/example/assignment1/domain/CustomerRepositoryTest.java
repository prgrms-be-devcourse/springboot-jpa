package com.example.assignment1.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    Customer customer;

    @BeforeEach
    void setup() {
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("YoonOh");
        customer.setLastName("Jung");
    }

    @AfterEach
    void clear() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("고객 정보를 저장할 수 있다.")
    void insertTest() {
        // given
        customerRepository.save(customer);

        // when
        var findCustomer = customerRepository.findById(customer.getId());

        // then
        assertThat(findCustomer, not(Optional.empty()));
        assertThat(customer, samePropertyValuesAs(findCustomer.get()));

    }

    @Test
    @DisplayName("ID로 고객 정보를 확인할 수 있다.")
    void findByIdTest() {
        // given
        customerRepository.save(customer);

        // when
        var findCustomer = customerRepository.findById(customer.getId());

        assertThat(findCustomer, not(Optional.empty()));
        assertThat(customer, samePropertyValuesAs(findCustomer.get()));
    }

    @Test
    @DisplayName("고객의 정보를 수정할 수 있다.")
    void updateTest() {
        // given
        customerRepository.save(customer);

        // when
        var findCustomer = customerRepository.findById(customer.getId()).get();
        findCustomer.setLastName("Kim");
        customerRepository.save(findCustomer);

        var afterModified = customerRepository.findById(customer.getId()).get();

        // then
        assertThat(findCustomer.getLastName(), is(afterModified.getLastName()));
    }

    @Test
    @DisplayName("고객 ID로 특정 고객의 정보를 삭제할 수 있다.")
    void deleteTest() {
        // given
        customerRepository.save(customer);

        // when
        customerRepository.deleteById(customer.getId());

        // then
        assertThat(customerRepository.findAll().size(), is(0));
    }
}