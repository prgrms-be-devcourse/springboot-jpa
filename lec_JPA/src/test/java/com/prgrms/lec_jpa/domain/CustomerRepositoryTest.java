package com.prgrms.lec_jpa.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class CustomerRepositoryTest {

    private final Logger log = LoggerFactory.getLogger(CustomerRepositoryTest.class);

    @Autowired
    CustomerRepository customerRepository;

    Customer customer = new Customer(1, "jaehee", "Yu");

    @BeforeEach
    void tearDown() {

        customerRepository.deleteAll();
    }

    @Test
    void save_test() {

        customerRepository.save(customer);

        Customer entity = customerRepository.findById(customer.getId()).get();

        log.info(entity.getFirstName() + " " + entity.getLastName());
    }

    @Test
    void update_test() {

        Customer entity = customerRepository.save(customer);

        entity.updateName("foo", "poo");

        Customer updatedEntity = customerRepository.findById(customer.getId()).get();

        log.info(updatedEntity.getFirstName() + " " + updatedEntity.getLastName());
    }

    @Test
    void delete_test() {

        Customer entity = customerRepository.save(customer);

        customerRepository.delete(entity);

        Optional<Customer> deleteEntity = customerRepository.findById(customer.getId());

        Assertions.assertThat(deleteEntity).isEqualTo(Optional.empty());
    }

}