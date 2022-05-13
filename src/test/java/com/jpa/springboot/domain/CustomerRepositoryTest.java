package com.jpa.springboot.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void tearDown(){
        customerRepository.deleteAll();
    }

    @Test
    void insert_customer(){
        //Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("juhan");
        customer.setLastName("byeon");

        //When
        customerRepository.save(customer);

        //Then
        Customer entity = customerRepository.findById(1L).get();
        assertEquals("juhan",entity.getFirstName());
        assertEquals("byeon",entity.getLastName());
    }

    @Test
    void update_customer(){
        //Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("juhan");
        customer.setLastName("byeon");

        //When
        customerRepository.save(customer);

        //Then
        Customer findCustomer = customerRepository.findById(1L).get();
        findCustomer.setFirstName("Programmers");
        findCustomer.setLastName("Backend");

        customerRepository.save(findCustomer);

        Customer now = customerRepository.findById(1L).get();
        assertEquals("Programmers",now.getFirstName());
        assertEquals("Backend",now.getLastName());
    }

    @Test
    void select_all_customer(){
        //Given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("aaaa");
        customer1.setLastName("bbbb");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("cccc");
        customer2.setLastName("dddd");
        //When
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        //Then
        List<Customer> customers = customerRepository.findAll();
        org.assertj.core.api.Assertions.assertThat(customers).containsExactlyInAnyOrder(customer1, customer2);

    }


}