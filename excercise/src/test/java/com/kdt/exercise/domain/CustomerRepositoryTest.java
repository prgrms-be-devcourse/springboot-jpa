package com.kdt.exercise.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository repository;

    Customer customer;
    @BeforeEach
    void setUp() {
        customer = new Customer("kang", "wansu");
        repository.save(customer);
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
        //@DynamicUpdate로 인해 바뀐 필드만 수정한다. update customers set first_name=? where id=?
        entity.setFirstName("change");
        Assertions.assertThat(entity.getFirstName()).isEqualTo(customer.getFirstName());
    }

}