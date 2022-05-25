package com.kdt.lecture.customer;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    private static final Long VALID_ID = 1L;
    private static final String VALID_FULL_NAME = "hyoungukkim";
    private static final String VALID_UPDATE_FULL_NAME = "hyoungjoonkim";

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setLastName("kim");
        customer.setFirstName("hyounguk");

        customerRepository.save(customer);
    }

    @AfterEach
    void cleanUp() {
        customerRepository.deleteAll();
    }

    @Test
    void 저장_테스트() {
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setLastName("kim");
        customer.setFirstName("hyoungjoon");

        customerRepository.save(customer);
        Customer entity = customerRepository.findById(2L).get();
        Assertions.assertThat(entity.getFirstName() + entity.getLastName())
            .isEqualTo(customer.getFirstName() + customer.getLastName());
    }

    @Test
    void 조회_테스트() {
        Customer customer = customerRepository.findById(VALID_ID).get();
        Assertions.assertThat(customer.getFirstName() + customer.getLastName())
            .isEqualTo(VALID_FULL_NAME);
    }

    @Test
    @Transactional
    void 수정_테스트() {
        Customer customer = customerRepository.findById(VALID_ID).get();
        customer.setFirstName("hyoungjoon");

        Customer updatedCustomer = customerRepository.findById(VALID_ID).get();
        Assertions.assertThat(updatedCustomer.getFirstName() + updatedCustomer.getLastName())
            .isEqualTo(VALID_UPDATE_FULL_NAME);
    }

    @Test
    void 삭제_테스트() {
        customerRepository.deleteById(VALID_ID);
        Optional<Customer> customer = customerRepository.findById(VALID_ID);

        Assertions.assertThat(customer).isEmpty();
    }

}