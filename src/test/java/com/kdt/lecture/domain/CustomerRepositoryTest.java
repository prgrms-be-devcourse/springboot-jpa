package com.kdt.lecture.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    @Test
    void insert_Test() {
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
        assertThat(entity.getFirstName()).isEqualTo("kim");
        assertThat(entity.getLastName()).isEqualTo("gong");
    }

    @Test
    void update_Test() {
        //given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("kim");
        customer.setLastName("gong");
        repository.save(customer);

        Customer entity = repository.findById(1L).get();
        assertThat(entity.getFirstName()).isEqualTo("kim");
        assertThat(entity.getLastName()).isEqualTo("gong");

        customer.setFirstName("Lee");
        repository.save(customer);

        Customer update = repository.findById(1L).get();
        assertThat(update.getFirstName()).isEqualTo("Lee");
        assertThat(update.getLastName()).isEqualTo("gong");
    }

    @Test
    void delete_Test() {
        //given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("kim");
        customer.setLastName("gong");
        repository.save(customer);

        Customer entity = repository.findById(1L).get();
        assertThat(entity.getFirstName()).isEqualTo("kim");
        assertThat(entity.getLastName()).isEqualTo("gong");

        repository.delete(customer);
        Optional<Customer> delete_entity = repository.findById(1L);
        assertThat(delete_entity).isEmpty();
    }
}
