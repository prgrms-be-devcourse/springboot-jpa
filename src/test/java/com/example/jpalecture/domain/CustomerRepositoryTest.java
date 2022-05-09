package com.example.jpalecture.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("kang");
        customer.setLastName("honggu");
        return customer;
    }

    @Test
    void customer_저장() {
        Customer customer = getCustomer();

        customerRepository.save(customer);

        Optional<Customer> findCustomer = customerRepository.findById(1L);

        assertThat(findCustomer).isNotEmpty();
        assertThat(customer).usingRecursiveComparison().isEqualTo(findCustomer.get());
    }

    @Test
    void customer_조회() {
        Customer customer = getCustomer();
        customerRepository.save(customer);

        Optional<Customer> findCustomer = customerRepository.findById(1L);

        assertThat(findCustomer).isNotEmpty();
        assertThat(findCustomer.get()).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    void customer_수정() {
        Customer customer = getCustomer();
        customerRepository.save(customer);

        Customer foundById = customerRepository.findById(1L).get();
        foundById.setFirstName("Kang");
        foundById.setLastName("HongGu");

        Optional<Customer> findCustomer = customerRepository.findById(1L);
        assertThat(findCustomer).isNotEmpty();
        assertThat(findCustomer.get().getFirstName()).isEqualTo("Kang");
        assertThat(findCustomer.get().getLastName()).isEqualTo("HongGu");
    }

    @Test
    void customer_삭제() {
        Customer customer = getCustomer();
        customerRepository.save(customer);

        customerRepository.delete(customer);

        Optional<Customer> findCustomer = customerRepository.findById(1L);
        assertThat(findCustomer).isEmpty();
    }
}