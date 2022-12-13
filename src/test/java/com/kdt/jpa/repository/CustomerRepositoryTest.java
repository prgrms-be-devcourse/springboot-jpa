package com.kdt.jpa.repository;

import com.kdt.jpa.entity.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    Customer customer1;
    Customer customer2;
    Customer customer3;

    @BeforeEach
    void setUp() {
        customer1 = new Customer("fn1", "ln1");
        customer2 = new Customer("fn2", "ln2");
        customer3 = new Customer("fn3", "ln3");

        repository.save(customer1);
        repository.save(customer2);
        repository.save(customer3);
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void create_test() {
        Customer customer4 = new Customer("fn4", "ln4");

        repository.save(customer4);
        assertThat(customer4, is(repository.findById(customer4.getId()).get()));
    }

    @Test
    void update_test() {
        Customer customer = repository.findById(customer1.getId()).get();
        customer.changeFirstName("updated");

        assertThat(customer, is(repository.findById(customer.getId()).get()));
    }

    @Test
    void delete_test() {
        repository.delete(customer1);
        assertThat(repository.findById(customer1.getId()), is(Optional.empty()));
    }

    @Test
    void findById_test() {
        assertThat(repository.findById(customer1.getId()).get(), is(customer1));
    }

    @Test
    void findAll_test() {
        assertThat(repository.findAll().size(), is(3));
    }
}