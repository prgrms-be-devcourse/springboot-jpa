package com.example.demo.repository;

import com.example.demo.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void deleteAll() {
        customerRepository.deleteAll();
    }

    @Test
    void testCreate() {
        Customer customer = new Customer();
        customer.setFirstName("jung hyun");
        customer.setLastName("moon");

        Customer entity = customerRepository.save(customer);
        Customer entity1 = customerRepository.findById(entity.getCustomerId()).get();

        assertThat(entity1.getFirstName(), is(entity.getFirstName()));
        assertThat(entity1.getLastName(), is(entity.getLastName()));
    }

    @Test
    @Transactional
    void testUpdate() {
        Customer customer = new Customer();
        customer.setFirstName("jung hyun");
        customer.setLastName("moon");

        Customer entity = customerRepository.save(customer);
        entity.setFirstName("young hyun");
        entity.setLastName("Moon");

        Customer entity1 = customerRepository.getById(entity.getCustomerId());
        assertThat(entity1.getFirstName(), is(entity.getFirstName()));
        assertThat(entity1.getLastName(), is(entity.getLastName()));
    }

    @Test
    void testDelete() {
        Customer customer = new Customer();
        customer.setFirstName("young hyun");
        customer.setLastName("Moon");

        Customer entity = customerRepository.save(customer);
        customerRepository.deleteById(entity.getCustomerId());

        long count = customerRepository.count();
        assertThat(count, is(0L));
    }
}