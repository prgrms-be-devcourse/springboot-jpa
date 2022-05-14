package com.dojinyou.devcourse.springbootjpa;

import com.dojinyou.devcourse.springbootjpa.repository.CustomerRepository;
import com.dojinyou.devcourse.springbootjpa.repository.domain.CustomerEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class JPATest {

    @Autowired
    CustomerRepository repository;

    @BeforeEach
    void setUp() { }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void insert_Test() {
        // Given
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setFirst_name("dojin");
        customer.setLast_name("you");

        // When
        repository.save(customer);

        //Then
        CustomerEntity entity = repository.findById(1L).get();
        log.info("{} {}",  entity.getFirst_name(), entity.getLast_name());
    }

    @Test
    @Transactional
    void update_Test() {
        // Given
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setFirst_name("dojin");
        customer.setLast_name("you");
        repository.save(customer);

        // When
        CustomerEntity entity = repository.findById(1L).get();
        entity.setFirst_name("dojiri");
        entity.setLast_name("you2");


        //Then
        CustomerEntity updated = repository.findById(1L).get();
        log.info("{} {}",  updated.getFirst_name(), updated.getLast_name());
    }

}
