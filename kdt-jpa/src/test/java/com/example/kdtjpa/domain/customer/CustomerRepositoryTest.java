package com.example.kdtjpa.domain.customer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;
    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }
    @Test
    void test() {
        //Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Eugene");
        customer.setLastName("Park");
        //When
        customerRepository.save(customer);
        //Then
        Customer entity = customerRepository.findById(1L).get();
        log.info("{} {}", entity.getFirstName(), entity.getLastName());
    }
}
