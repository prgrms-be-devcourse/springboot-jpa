package com.programmers.kwonjoosung.springbootjpa.repository;

import com.programmers.kwonjoosung.springbootjpa.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Slf4j
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer generateTestCustomer() {
        return new Customer("joosung", "kwon");
    }

    @Test
    @DisplayName("Customer 저장 및 조회 테스트")
    void saveTest() {

        //given
        Customer customer = generateTestCustomer();
        customerRepository.save(customer);
        //when
        Customer savedCustomer = customerRepository.findById(customer.getId()).get();
        //then
        assertThat(savedCustomer)
                .hasFieldOrPropertyWithValue("firstName", customer.getFirstName())
                .hasFieldOrPropertyWithValue("lastName", customer.getLastName());
    }

    @Test
    @DisplayName("Customer 수정 테스트")
    void updateTest() {

        //given
        Customer customer = generateTestCustomer();
        Customer savedCustomer = customerRepository.save(customer);

        //when
        savedCustomer.changeFirstName("SUNGJOO");
        savedCustomer.changeLastName("KIM");
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();

        //then
        assertThat(updatedCustomer)
                .hasFieldOrPropertyWithValue("firstName", "SUNGJOO")
                .hasFieldOrPropertyWithValue("lastName", "KIM");
    }

    @Test
    @DisplayName("Customer 삭제 테스트")
    void delteTest() {

        //given
        Customer customer = generateTestCustomer();
        customerRepository.save(customer);
        //when
        customerRepository.delete(customer);
        boolean isCustomerExists = customerRepository.findById(customer.getId()).isPresent();
        //then
        assertThat(isCustomerExists).isFalse();
    }

}