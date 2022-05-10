package com.prgrms.springbootjpa.domain;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@SpringBootTest

class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Test
    @DisplayName("고객 create Read 테스트")
    @Transactional
    void createReadTest() {
        Customer customer = new Customer(1L, "jerry", "hong");
        customerRepository.save(customer);

        Customer entity = customerRepository.findById(1L).get();
        assertThat(entity, samePropertyValuesAs(entity));
    }

    @Test
    @DisplayName("고객 update 테스트")
    @Transactional
    void updateTest() {
        Customer customer = new Customer(null,"jerry", "hong");
        customerRepository.save(customer);

        customer.setFirstName("yuseok");

        Customer entity2 = customerRepository.findById(1L).get();
        assertThat(entity2.getFirstName(), is("yuseok"));
    }

    @Test
    @DisplayName("고객 delete 테스트")
    @Transactional
    void deleteTest() {
        Customer customer = new Customer(1L, "jerry", "hong");
        customerRepository.save(customer);

        customerRepository.delete(customer);
        Optional<Customer> ret = customerRepository.findById(1L);
        assertThat(ret, is(Optional.empty()));
    }
}