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
    @DisplayName("고객 CRUD 테스트")
    @Transactional
    void crudTest() {
        Customer customer = new Customer(1L, "jerry", "hong");
        customerRepository.save(customer);

        Customer entity = customerRepository.findById(1L).get();
        assertThat(entity, samePropertyValuesAs(entity));

        entity.setFirstName("yuseok");
        Customer entity2 = customerRepository.findById(1L).get();
        assertThat(entity2.getFirstName(), is("yuseok"));

        customerRepository.delete(entity2);
        Optional<Customer> entity3 = customerRepository.findById(1L);
        assertThat(entity3, is(Optional.empty()));
    }
}