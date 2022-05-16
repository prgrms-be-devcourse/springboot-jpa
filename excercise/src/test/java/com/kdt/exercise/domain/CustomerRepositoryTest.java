package com.kdt.exercise.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository repository;

    @PersistenceContext
    EntityManager em;

    Customer customer, savedCustomer;

    @BeforeEach
    void setUp() {
        customer = new Customer("kang", "wansu");
        savedCustomer = repository.save(customer);
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void customer_저장() {
        //then
        Customer entity = repository.findAll().get(0);
        Assertions.assertThat(entity.getFirstName()).isEqualTo(customer.getFirstName());
    }

    @Test
    void customer_수정() {
        //then
        Customer entity = repository.findAll().get(0);
        entity.setFirstName("change");
        Assertions.assertThat(entity.getFirstName()).isEqualTo(customer.getFirstName());
    }

    @Test
    @Transactional
    void customer_저장한다() {
        repository.save(new Customer("kang","wansu"));
    }
}