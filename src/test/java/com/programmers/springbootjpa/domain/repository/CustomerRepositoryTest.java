package com.programmers.springbootjpa.domain.repository;

import com.programmers.springbootjpa.domain.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DisplayName("Customer Repository Test")
class CustomerRepositoryTest {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    CustomerRepository customerRepository;
    private Customer customer;

    @BeforeEach
    void init() {
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("David");
        customer.setLastName("Lee");
    }

    @Test
    @DisplayName("고객을 생성 및 조회할 수 있다.")
    void testSaveAndFind() {
        // Act
        customerRepository.save(customer);
        Optional<Customer> actualResult = customerRepository.findById(customer.getId());
        // Assert
        assertThat(actualResult).isPresent().map(Customer::getId).hasValue(customer.getId());
        log.info("{} {}", actualResult.get().getFirstName(), actualResult.get().getLastName());
    }

    @Test
    @DisplayName("고객을 업데이트 할 수 있다.")
    void testUpdate() {
        // Arrange
        customerRepository.save(customer);
        // Act
        Customer expectedResult = customerRepository.findById(customer.getId()).get();
        expectedResult.setLastName("Park");
        // Assert
        Customer actualResult = customerRepository.findById(customer.getId()).get();
        assertThat(actualResult.getLastName()).isEqualTo(expectedResult.getLastName());
        log.info("{} {}", actualResult.getFirstName(), actualResult.getLastName());
    }

    @Test
    @DisplayName("고객을 삭제할 수 있다.")
    void testDelete() {
        // Arrange
        customerRepository.save(customer);
        // Act
        customerRepository.delete(customer);
        // Assert
        Optional<Customer> actualResult = customerRepository.findById(customer.getId());
        assertThat(actualResult).isEmpty();
    }
}