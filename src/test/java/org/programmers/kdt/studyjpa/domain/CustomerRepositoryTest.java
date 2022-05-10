package org.programmers.kdt.studyjpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    void createCustomerTest() {
        //Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("honggu");
        customer.setLastName("kang");

        //When
        var save = repository.save(customer);

        //Then
        var findCustomer = repository.findById(1L).orElseThrow();
        assertThat(save).isEqualTo(findCustomer);
    }
}