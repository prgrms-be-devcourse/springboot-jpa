package com.example.demo.repository;

import com.example.demo.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Slf4j
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    void insert_test() {
        Customer customer = createCustomer("yerim", "lee");


        Customer savedCustomer = repository.save(customer);
        log.info("{} {} {}", savedCustomer.getId(), savedCustomer.getFirstName(), savedCustomer.getLastName());
    }

    @Test
    void select_test() {
        Customer customer = createCustomer("yerim", "lee");

        Customer savedCustomer = repository.save(customer);

        Optional<Customer> findById = repository.findById(savedCustomer.getId());

        assertThat(findById).isNotEmpty();
        assertThat(findById.get().getId()).isEqualTo(savedCustomer.getId());
        log.info("{} {} {}", findById.get().getId(), findById.get().getFirstName(), findById.get().getLastName());
    }

    @Test
    void update_test() {
        Customer customer = createCustomer("yerim", "lee");

        Customer savedCustomer = repository.save(customer);

        savedCustomer.changeName("change", "name");

        assertThat(savedCustomer.getFirstName()).isEqualTo("change");
        log.info("{} {} {}", savedCustomer.getId(), savedCustomer.getFirstName(), savedCustomer.getLastName());
    }

    @Test
    void delete_test() {
        Customer customer = createCustomer("yerim", "lee");
        repository.save(customer);
        repository.delete(customer);

        assertThat(repository.findAll().isEmpty()).isTrue();
    }

    private Customer createCustomer(String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName);
        return customer;
    }

}
