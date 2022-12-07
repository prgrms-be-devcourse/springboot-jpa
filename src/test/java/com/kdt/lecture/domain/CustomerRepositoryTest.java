package com.kdt.lecture.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    @BeforeEach
    void tearDown() {
        repository.deleteAll();
    }

    @DisplayName("고객을 저장할 수 있다.")
    @Test
    void saveTest() {
        // given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("teakseung");
        customer.setLastName("lee");

        // when
        repository.save(customer);

        // then
        Optional<Customer> maybeCustomer = repository.findById(1L);
        assertThat(maybeCustomer)
                .isPresent();
    }

    @DisplayName("고객을 조회할 수 있다.")
    @Test
    void findTest() {
        // given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("teakseung");
        customer.setLastName("lee");
        repository.save(customer);

        // when
        Optional<Customer> maybeCustomer = repository.findById(1L);

        // then
        assertThat(maybeCustomer)
                .isPresent();
    }

    @DisplayName("고객 정보를 수정할 수 있다.")
    @Test
    @Transactional
    void updateTest() {
        // given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("teakseung");
        customer.setLastName("lee");
        repository.save(customer);

        // when
        Customer entity = repository.findById(1L).get();
        entity.setFirstName("taekho");

        // then
        assertThat(entity.getFirstName())
                .isEqualTo("taekho");
    }

    @DisplayName("고객 정보를 삭제할 수 있다.")
    @Test
    void deleteTest() {
        // given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("teakseung");
        customer.setLastName("lee");
        repository.save(customer);

        // when
        repository.delete(customer);

        // then
        assertThat(repository.findById(1L))
                .isEmpty();
    }
}