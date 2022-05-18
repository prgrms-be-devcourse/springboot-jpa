package com.example.jpasettingpractice.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Transactional
    void SAVE_TEST() {
        // Given
        Customer customer = new Customer();
        customer.setFirstName("changho");
        customer.setLastName("lee");

        // When
        Customer save = customerRepository.save(customer);

        // Then
        assertThat(save).isEqualTo(customer);
    }

    @Test
    @Transactional
    void FIND_BY_ID_TEST() {
        // Given
        Customer customer = new Customer();
        customer.setFirstName("changho");
        customer.setLastName("lee");

        // When
        customerRepository.save(customer);

        // Then
        Optional<Customer> entity = customerRepository.findById(customer.getId());
        assertThat(entity).contains(customer);
    }

    @Test
    @Transactional
    void FIND_ALL_TEST() {
        // Given
        Customer customer1 = new Customer();
        customer1.setFirstName("changho");
        customer1.setLastName("lee");

        Customer customer2 = new Customer();
        customer2.setFirstName("brand");
        customer2.setLastName("lee");

        // When
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        // Then
        var list = customerRepository.findAll();
        assertThat(list).hasSize(2);
    }

    @Test
    @Transactional
    void UPDATE_TEST() {
        // Given
        Customer customer = new Customer();
        customer.setFirstName("changho");
        customer.setLastName("lee");
        customerRepository.save(customer);

        // When
        var byId = customerRepository.findById(customer.getId());
        assertThat(byId).isPresent();
        byId.get().setFirstName("brandon");

        // Then
        var updated = customerRepository.findById(byId.get().getId());
        assertThat(updated).contains(customer);
    }

    @Test
    @Transactional
    void DELETE_BY_ID_TEST() {
        // Given
        Customer customer = new Customer();
        customer.setFirstName("changho");
        customer.setLastName("lee");
        customerRepository.save(customer);

        // When
        customerRepository.deleteById(customer.getId());

        // Then
        var all = customerRepository.findAll();
        assertThat(all).isEmpty();
    }

    @Test
    @Transactional
    void DELETE_BY_ENTITY_TEST() {
        // Given
        Customer customer = new Customer();
        customer.setFirstName("changho");
        customer.setLastName("lee");
        customerRepository.save(customer);

        // When
        customerRepository.delete(customer);

        // Then
        var all = customerRepository.findAll();
        assertThat(all).isEmpty();
    }
}