package com.kdt.weeklyjpa.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Test
    @DisplayName("Customers_조회_테스트")
    void getCustomerTest() {
        // given
        Customer customer1 = new Customer("Youngmyung", "Kim");
        Customer customer2 = new Customer("Test1", "Hong");
        Customer customer3 = new Customer("Test2", "Kwon");


        // when
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
        List<Customer> customers = customerRepository.findAll();

        // then
        assertThat(customers).hasSize(3);
    }

    @Test
    @DisplayName("Customer_update_테스트")
    void updateCustomerTest() {
        // given
        Customer customer1 = new Customer("Youngmyung", "Kim");


        // when

        // then
    }

}