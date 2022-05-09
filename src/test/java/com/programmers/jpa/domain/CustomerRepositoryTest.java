package com.programmers.jpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    @DisplayName("저장 테스트")
    void saveTest() {
        Customer customer = new Customer(1L, "hanju", "lee");

        customerRepository.save(customer);

        Customer savedCustomer = customerRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디"));

        assertAll(
                () -> assertThat(savedCustomer.getId()).isEqualTo(customer.getId()),
                () -> assertThat(savedCustomer.getFirstName()).isEqualTo(customer.getFirstName()),
                () -> assertThat(savedCustomer.getLastName()).isEqualTo(customer.getLastName())
        );
    }

}