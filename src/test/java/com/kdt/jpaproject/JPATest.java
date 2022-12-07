package com.kdt.jpaproject;

import com.kdt.jpaproject.domain.Customer;
import com.kdt.jpaproject.domain.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
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
    void INSERT_TEST() {
        //Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("youngbin");
        customer.setLastName("kim");

        // When
        repository.save(customer);

        // Then
        Customer entity = repository.findById(1L).get();
        Assertions.assertThat(entity.getFirstName()).isEqualTo("youngbin");
    }

    @Test
    @Transactional
    void UPDATE_TEST() {
        //Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("youngbin");
        customer.setLastName("kim");
        repository.save(customer);

        //When
        Customer entity = repository.findById(1L).get();
        entity.setFirstName("guppy");
        entity.setLastName("hong");

        Customer updated = repository.findById(1L).get();
        Assertions.assertThat(updated.getFirstName()).isEqualTo("guppy");
    }

    @Test
    void DELETE_TEST() {
        //Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("youngbin");
        customer.setLastName("kim");
        repository.save(customer);

        // When
        repository.deleteById(1L);

        // Then
        Assertions.assertThat(repository.count()).isEqualTo(0);
    }

}
