package com.sehee.weeklyjpa;

import com.sehee.weeklyjpa.domain.Customer;
import com.sehee.weeklyjpa.domain.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class JpaTest {
    @Autowired
    CustomerRepository repository;

    Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Sehee");
        customer.setLastName("Lee");
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("고객을 저장하고 조회할 수 있다.")
    void insertTest() {
        //When
        repository.save(customer);

        //Then
        Customer retrievedCustomer = repository.findById(customer.getId()).get();
        log.info("{} {}", retrievedCustomer.getFirstName(), retrievedCustomer.getLastName());
    }

    @Test
    @Transactional
    @DisplayName("고객을 업데이트할 수 있다.")
    void updateTest() {
        //Given
        repository.save(customer);

        //When
        Customer retrievedCustomer = repository.findById(customer.getId()).get();
        retrievedCustomer.setLastName("Kim");

        //Then
        Customer updatedCustomer = repository.findById(customer.getId()).get();
        log.info("{} {}", updatedCustomer.getFirstName(), updatedCustomer.getLastName());
    }
    @Test
    @Transactional
    @DisplayName("고객을 삭제할 수 있다.")
    void deleteTest() {
        //Given
        repository.save(customer);

        //When
        repository.delete(customer);

        //Then
        Optional<Customer> customerOptional = repository.findById(customer.getId());
        assertThat(customerOptional).isEmpty();
    }
}
