package com.kdt.lecture.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

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
        Customer entity = repository.findById(1L).get();
        log.info("{} {}", entity.getFirstName(), entity.getLastName());

    }

    @Test
    void findTest() {
        // given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("teakseung");
        customer.setLastName("lee");
        repository.save(customer);

        // when
        Customer entity = repository.findById(1L).get();

        // then
        log.info("{} {}", entity.getFirstName(), entity.getLastName());
    }

    @Test
    void updateTest() {
        // given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("teakseung");
        customer.setLastName("lee");
        repository.save(customer);

        // when
        customer.setLastName("taekho");
        Customer entity = repository.findById(1L).get();

        // then
        log.info("{} {}", entity.getFirstName(), entity.getLastName());
    }

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
        assertEquals(repository.findById(1L).isEmpty(), true);

    }
}