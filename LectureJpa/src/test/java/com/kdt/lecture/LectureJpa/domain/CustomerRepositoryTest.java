package com.kdt.lecture.LectureJpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    @DisplayName("create와 read 테스트")
    void createTest() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("kim");
        customer.setLastName("kiwoong");

        customerRepository.save(customer);
        Customer findCustomer = customerRepository.findById(1L).get();

        log.info("{}, {}", findCustomer.getLastName(), findCustomer.getFirstName());

        assertThat(findCustomer).isEqualTo(customer);
    }

    @Test
    @DisplayName("update 테스트")
    void updateTest() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("kim");
        customer.setLastName("kiwoong");

        Customer saveCustomer = customerRepository.save(customer);
        saveCustomer.setLastName("kiwoong1");
        Customer findCustomer = customerRepository.findById(1L).get();
        assertThat(findCustomer.getLastName()).isEqualTo("kiwoong1");
    }

    @Test
    @DisplayName("delete 테스트")
    void deleteTest() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("kim");
        customer.setLastName("kiwoong");

        customerRepository.save(customer);
        Customer findCustomer = customerRepository.findById(1L).get();
        assertThat(customer).isEqualTo(findCustomer);
        
        customerRepository.deleteById(1L);
        assertThat(customerRepository.findById(1L).isEmpty()).isTrue();
    }
}