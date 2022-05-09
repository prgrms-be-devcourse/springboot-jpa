package com.programmers.lecture.domain.repository;

import com.programmers.lecture.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    Customer baseCustomer;

    @BeforeAll
    void setUp(){
        baseCustomer = new Customer();
        baseCustomer.setId(1L);
        baseCustomer.setFirstName("kyung-il");
        baseCustomer.setLastName("jung");
    }

    @Test
    @DisplayName("고객 생성 테스트")
    void testInsert(){
        customerRepository.save(baseCustomer);

        Optional<Customer> maybeCustomer = customerRepository.findById(1L);
        Customer customer = maybeCustomer.orElseThrow(() -> new NullPointerException("고객을 조회할 수 없다."));

        assertThat(customer.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("고객 삭제 테스트")
    void testDelete(){
        customerRepository.save(baseCustomer);
        customerRepository.deleteById(1L);

        assertThrows(NullPointerException.class ,
                () -> customerRepository.findById(1L).orElseThrow(NullPointerException::new));
    }

    @Test
    @DisplayName("고객 수정 테스트")
    @Transactional
    void testUpdate(){
        customerRepository.save(baseCustomer);

        Optional<Customer> maybeCustomer = customerRepository.findById(1L);
        Customer customer = maybeCustomer.orElseThrow(() -> new NullPointerException("고객을 조회할 수 없다."));

        customer.setLastName("j");

        assertThat(customer.getLastName()).isEqualTo("j");
    }
}