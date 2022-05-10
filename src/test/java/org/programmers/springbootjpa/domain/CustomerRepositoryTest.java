package org.programmers.springbootjpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    void test() {
        //Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("honggu");
        customer.setLastName("kang");

        //When
        repository.save(customer);

        //Then
        Customer foundcustomer = repository.findById(1L).get();
        log.info("{} {}", foundcustomer.getFirstName(), foundcustomer.getLastName());
    }
}