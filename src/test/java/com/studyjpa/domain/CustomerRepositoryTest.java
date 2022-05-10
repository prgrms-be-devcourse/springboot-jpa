package com.studyjpa.domain;

import com.studyjpa.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @ParameterizedTest(name = "{1} create test")
    @CsvSource({"1, moosong, song"})
    void create_test(long pk, String firstName, String lastName) {
        // Given
        Customer customer = new Customer(pk, firstName, lastName);
        // When
        customerRepository.save(customer);
        // Then
        Customer entity = customerRepository.findById(pk).get();
        log.info("{} {}", entity.getLastName(), entity.getFirstName());
    }

    @Test
    void read_test() {
        // Given
        Customer customer1 = new Customer(1L, "moosong", "song");
        Customer customer2 = new Customer(2L, "ms", "s");
        // When
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        // Then
        List<Customer> list = customerRepository.findAll();
        for (Customer customer : list) {
            log.info("{} {}", customer.getLastName(), customer.getFirstName());
        }
    }

    @ParameterizedTest(name = "{1} update test")
    @CsvSource({"1, moosong, song"})
    @Transactional
    void update_test(long pk, String firstName, String lastName) {
        // Given
        Customer customer = new Customer(pk, firstName, lastName);
        customerRepository.save(customer);
        // When
        Customer entity = customerRepository.findById(pk).get();
        entity.setLastName("kim");
        // Then
        Customer update = customerRepository.findById(pk).get();
        log.info("{} {}", update.getLastName(), update.getFirstName());
    }

    @ParameterizedTest(name = "{1} update test")
    @CsvSource({"1, moosong, song"})
    void delete_test(long pk, String firstName, String lastName) {
        // Given
        Customer customer = new Customer(pk, firstName, lastName);
        customerRepository.save(customer);
        List<Customer> before = customerRepository.findAll();
        if (before.size() != 0) {
            for (Customer item : before) {
                log.info("{} {}", item.getLastName(), item.getFirstName());
            }
        }
        // When
        customerRepository.delete(customer);
        // Then
        List<Customer> after = customerRepository.findAll();
        if (after.size() == 0) {
            log.info("Delete!");
        }
    }
}