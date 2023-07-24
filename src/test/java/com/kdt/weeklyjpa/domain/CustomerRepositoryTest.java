package com.kdt.weeklyjpa.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("Customer_생성_테스트")
    void saveTest() {
        // given
        Customer customer = new Customer("Youngmyung", "Kim");

        // when
        Customer saved = customerRepository.save(customer);
        Optional<Customer> foundById = customerRepository.findById(saved.getId());

        // then
        assertThat(foundById)
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }

}