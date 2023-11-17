package com.devcourse.springbootjpaweekly.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.devcourse.springbootjpaweekly.domain.Customer;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;
    Customer customer;

    @BeforeEach
    void setup() {
        customer = Customer.builder()
                .firstName("name")
                .lastName("kim")
                .email("kim@kim.kim")
                .build();
    }

    @AfterEach
    void cleanup() {
        customerRepository.deleteAll();
    }

    @DisplayName("고객을 저장한다.")
    @Test
    void testSave() {
        // given // when
        customerRepository.save(customer);

        // then
        long count = customerRepository.count();

        assertThat(count).isEqualTo(1);
    }

    @DisplayName("ID로 고객을 조회한다.")
    @Test
    void testFindById() {
        // given
        customerRepository.save(customer);

        // when
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());

        // then
        assertThat(optionalCustomer).isNotEmpty();

        Customer actualCustomer = optionalCustomer.get();

        assertThat(actualCustomer).usingRecursiveComparison()
                .isEqualTo(customer);
    }
}
