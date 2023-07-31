package com.example.springbootjpaweekly.domain.repository;

import com.example.springbootjpaweekly.domain.entity.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
//JPA 관련 테스트
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer savedCustomer;

    @BeforeEach
    public void setUp() {
        Customer customer = Customer.builder()
                .firstName("민")
                .lastName("승현")
                .build();

        savedCustomer = customerRepository.save(customer);
    }

    @Test
    public void 고객_조회_테스트() {
        Optional<Customer> foundCustomer = customerRepository.findById(savedCustomer.getId());
        Assertions.assertTrue(foundCustomer.isPresent());
        foundCustomer.ifPresent(customer -> {
            Assertions.assertEquals(savedCustomer.getFirstName(), customer.getFirstName());
            Assertions.assertEquals(savedCustomer.getLastName(), customer.getLastName());
        });
    }

    @Test
    public void 고객_업데이트_테스트() {
        String updatedFirstName = "승승현";
        String updatedLastName = "민";

        Customer updatedCustomer = Customer.builder()
                .id(savedCustomer.getId())
                .firstName(updatedFirstName)
                .lastName(updatedLastName)
                .build();

        customerRepository.save(updatedCustomer);

        Optional<Customer> foundUpdatedCustomer = customerRepository.findById(savedCustomer.getId());
        Assertions.assertTrue(foundUpdatedCustomer.isPresent());
        foundUpdatedCustomer.ifPresent(customer -> {
            Assertions.assertEquals(updatedFirstName, customer.getFirstName());
            Assertions.assertEquals(updatedLastName, customer.getLastName());
        });
    }

    @Test
    public void 고객_삭제_테스트() {
        customerRepository.delete(savedCustomer);

        Optional<Customer> deletedCustomer = customerRepository.findById(savedCustomer.getId());
        Assertions.assertFalse(deletedCustomer.isPresent());
    }

    @Test
    public void 이름_유효성_테스트() {
        Customer invalidCustomer = Customer.builder()
                .firstName("1234") // 숫자가 포함된 이름은 유효하지 않음
                .lastName("민")
                .build();

        Assertions.assertThrows(ConstraintViolationException.class, () -> customerRepository.save(invalidCustomer));
    }

    @Test
    public void 성_유효성_테스트() {
        Customer invalidCustomer = Customer.builder()
                .firstName("승현")
                .lastName("1234") // 숫자가 포함된 성은 유효하지 않음
                .build();

        Assertions.assertThrows(ConstraintViolationException.class, () -> customerRepository.save(invalidCustomer));
    }
}