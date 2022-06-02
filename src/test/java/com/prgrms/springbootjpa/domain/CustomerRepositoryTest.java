package com.prgrms.springbootjpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@Slf4j
@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Test
    @DisplayName("고객 create Read 테스트")
    void createReadTest() {
        Customer customer = new Customer("jerry", "hong");
        customerRepository.save(customer);

        String name = customer.getFirstName();
        log.info("firstName = {}", name);

        Customer entity = customerRepository.findById(customer.getId()).get();
        assertThat(entity, samePropertyValuesAs(entity));
    }

    @Test
    @DisplayName("고객 update 테스트")
    void updateTest() {
        Customer customer = new Customer("jerry", "hong");
        customerRepository.save(customer);

        customer.changeFirstName("yuseok");

        Customer updatedEntity = customerRepository.findById(customer.getId()).get();
        assertThat(updatedEntity.getFirstName(), is("yuseok"));
    }

    @Test
    @DisplayName("고객 delete 테스트")
    void deleteTest() {
        Customer customer = new Customer("jerry", "hong");
        customerRepository.save(customer);

        customerRepository.delete(customer);

        Optional<Customer> nonExistentCustomer = customerRepository.findById(customer.getId());
        assertThat(nonExistentCustomer, is(Optional.empty()));
    }
}