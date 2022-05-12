package com.example.chapter1.domain.customer.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    @Test
    void 저장하고_조회할수_있다() {
        //given
        Customer customer = new Customer("deukyun", "nam");

        //when
        repository.save(customer);

        //then
        Customer found = repository.findById(customer.getId()).get();
        assertThat(found.getId()).isEqualTo(customer.getId());
        assertThat(found.getFirstName()).isEqualTo("deukyun");
        assertThat(found.getLastName()).isEqualTo("nam");
    }
}