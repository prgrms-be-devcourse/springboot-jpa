package com.prgrms.springbootjpa.domain.customer;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import com.prgrms.springbootjpa.domain.customer.Customer;
import com.prgrms.springbootjpa.domain.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer("jerry", "hong");
        customerRepository.save(customer);
    }

    @Test
    @DisplayName("고객 create Read 테스트")
    void createReadTest() {
        Customer entity = customerRepository.findById(customer.getId()).get();
        assertThat(entity, samePropertyValuesAs(entity));
    }

    @Test
    @DisplayName("고객 update 테스트")
    void updateTest() {
        customer.changeFirstName("yuseok");

        Customer entity2 = customerRepository.findById(customer.getId()).get();
        assertThat(entity2.getFirstName(), is("yuseok"));
    }

    @Test
    @DisplayName("고객 delete 테스트")
    void deleteTest() {
        customerRepository.delete(customer);
        Optional<Customer> ret = customerRepository.findById(customer.getId());
        assertThat(ret, is(Optional.empty()));
    }
}