package com.blessing333.kdtjpa.domain;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository repository;
    @Autowired
    private EntityManager em;

    @AfterEach
    void init() {
        repository.deleteAll();
    }

    @Test
    void saveTest() {
        Customer customer = new Customer(1L, "Minjae", "Lee");

        repository.save(customer);

        Assertions.assertDoesNotThrow(() -> {
            Customer found = repository.findById(1L).get();
            assertThat(found.getId()).isEqualTo(1L);
            assertThat(found.getFirstName()).isEqualTo("Minjae");
            assertThat(found.getLastName()).isEqualTo("Lee");
        });
    }

    @Test
    void findByIdTest() {
        Customer customer = new Customer(1L, "Minjae", "Lee");
        repository.save(customer);
        Optional<Customer> found = repository.findById(1L);

        assertThat(found).contains(customer);
    }

    @Test
    void updateTest() {
        Customer customer = new Customer(1L, "Minjae", "Lee");
        Customer saved = repository.save(customer);

        saved.changeFirstName("Jaemin");

        Customer found = repository.findById(1L).get();
        assertThat(em.contains(customer)).isTrue();
        assertThat(found.getFirstName()).isEqualTo("Jaemin");
    }

    @Test
    void deleteTest() {
        Customer customer = new Customer(1L, "Minjae", "Lee");
        Customer saved = repository.save(customer);

        repository.delete(saved);

        Optional<Customer> found = repository.findById(1L);
        assertThat(found).isEmpty();
    }

    @Test
    void findAllTest() {
        Customer customer = new Customer(1L, "Minjae", "Lee");
        Customer customer2 = new Customer(2L, "JaeMin", "Lee");
        repository.saveAll(Lists.newArrayList(customer, customer2));

        // When
        List<Customer> selectedCustomers = repository.findAll();

        // Then
        assertThat(selectedCustomers).hasSize(2).contains(customer, customer2);
    }
}