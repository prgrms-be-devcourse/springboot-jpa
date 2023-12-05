package org.programmers.springjpa.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.programmers.springjpa.domain.entity.Customer;
import org.programmers.springjpa.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Jiin");
        customer.setLastName("Hong");
    }

    @Test
    void save_and_find() {
        customerRepository.save(customer);
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());
        assertThat(optionalCustomer.isPresent()).isTrue();
        assertThat(optionalCustomer.get().getId()).isEqualTo(customer.getId());
        assertThat(optionalCustomer.get().getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(optionalCustomer.get().getLastName()).isEqualTo(customer.getLastName());
    }

    @Test
    void update() {
        customerRepository.save(customer);
        Customer expected = customerRepository.findById(customer.getId()).get();
        expected.setLastName("Choi");

        Customer actual = customerRepository.findById(customer.getId()).get();
        assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        assertThat(actual.getFirstName()).isEqualTo(customer.getFirstName());
    }

    @Test
    void delete() {
        customerRepository.save(customer);
        customerRepository.delete(customer);
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());
        assertThat(optionalCustomer).isNotPresent();
    }
}
