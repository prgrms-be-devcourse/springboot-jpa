package com.programmers.jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer("yiseul", "park");
    }

    @Test
    @DisplayName("고객을 저장한다.")
    void saveTest() {
        // when
        Customer result = customerRepository.save(customer);

        // then
        assertThat(result.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(result.getLastName()).isEqualTo(customer.getLastName());
    }

    @Test
    @DisplayName("고객을 모두 조회한다.")
    void findAllTest() {
        // given
        customerRepository.save(customer);

        // when
        List<Customer> result = customerRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("고객 아이디로 조회한다.")
    void findByIdTest() {
        // given
        Customer savedCustomer = customerRepository.save(customer);

        // when
        Customer result = customerRepository.findById(savedCustomer.getId()).get();

        // then
        assertThat(result).isEqualTo(savedCustomer);
    }

    @Test
    @DisplayName("고객 이름을 수정한다.")
    void updateTest() {
        // given
        Customer savedCustomer = customerRepository.save(customer);
        String updateFirstName = "seul";

        // when
        savedCustomer.updateFirstName(updateFirstName);

        // then
        Customer result = customerRepository.findById(savedCustomer.getId()).get();
        assertThat(result.getFirstName()).isEqualTo(updateFirstName);
    }

    @Test
    @DisplayName("고객 정보를 삭제한다.")
    void deleteByIdTest() {
        // given
        Customer savedCustomer = customerRepository.save(customer);

        // when
        customerRepository.deleteById(savedCustomer.getId());

        // then
        boolean result = customerRepository.existsById(savedCustomer.getId());
        assertThat(result).isFalse();
    }
}