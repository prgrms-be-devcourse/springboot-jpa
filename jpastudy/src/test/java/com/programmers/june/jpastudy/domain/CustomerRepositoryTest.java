package com.programmers.june.jpastudy.domain;

import com.programmers.june.jpastudy.domain.customer.Customer;
import com.programmers.june.jpastudy.domain.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    void 테스트용_고객_생성() {
        customer = Customer.builder()
                .firstName("test")
                .lastName("kim")
                .build();
    }

    @Test
    void 고객_조회() {
        // given
        Customer savedCustomer = customerRepository.save(customer);

        // when
        Customer retrievedCustomer = customerRepository.findById(savedCustomer.getId())
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        // then
        assertThat(savedCustomer, samePropertyValuesAs(retrievedCustomer));
    }

    @Test
    void 고객_이름_부분_수정() {
        // given
        Customer savedCustomer = customerRepository.save(customer);

        // when
        savedCustomer.changeFirstName("junhyuk");
        savedCustomer.changeLastName("choi");

        Customer changedCustomer = customerRepository.findById(savedCustomer.getId())
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        // then
        assertEquals("junhyuk", changedCustomer.getFirstName());
        assertEquals("choi", changedCustomer.getLastName());
    }

    @Test
    void 고객_이름_전체_수정() {
        // given
        Customer savedCustomer = customerRepository.save(customer);

        // when
        savedCustomer.changeFullName("junhyuk", "choi");

        Customer changedCustomer = customerRepository.findById(savedCustomer.getId())
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        // then
        assertEquals("junhyuk", changedCustomer.getFirstName());
        assertEquals("choi", changedCustomer.getLastName());
    }

    @Test
    void 고객_삭제() {
        // given
        Customer savedCustomer = customerRepository.save(customer);
        Long customerId = savedCustomer.getId();

        // when
        customerRepository.deleteById(customerId);

        // then
        assertThrows(RuntimeException.class,
                () -> customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다.")));
    }
}