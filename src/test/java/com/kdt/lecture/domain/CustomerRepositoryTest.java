package com.kdt.lecture.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    @Test
    void test() {
        //given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("kim");
        customer.setLastName("gong");

        //when
        repository.save(customer);

        //then
        Customer entity = repository.findById(1L).get();
        log.info("{}{}", entity.getFirstName(), entity.getLastName());
    }
}