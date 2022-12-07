package com.example.springbootjpamission1.domain.customer.repository;

import com.example.springbootjpamission1.domain.customer.entity.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@Slf4j
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setup() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("customer를 save하면 findAll를 하면 Customer가 들어있다.")
    void saveTestAndFindTest() {
        //given
        Customer customer = Customer.builder()
                .firstName("장")
                .lastName("주")
                .build();

        //when
        customerRepository.save(customer);
        List<Customer> customers = customerRepository.findAll();


        //then
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    @DisplayName("customerRepository.update는 customer의 이름을 변경할 수 있다.")
    @Transactional
    void updateTest() {
        //given
        Customer customer = Customer.builder()
                .id(1L)
                .firstName("장")
                .lastName("주")
                .build();

        //when
        customerRepository.save(customer);
        Customer updateCustomer = customerRepository.findById(1L).get();
        updateCustomer.update("기","웅");

        Customer foundCustomer = customerRepository.findById(1L).get();

        //then
        assertThat(foundCustomer.getFirstName(), is("기"));
    }

    @Test
    @DisplayName("customerRepository.delete는 데이터베이스의 모든 customer를 삭제한다.")
    void deleteTest() {
        //given
        Customer customer = Customer.builder()
                .id(1L)
                .firstName("장")
                .lastName("주")
                .build();

        //when
        customerRepository.save(customer);
        customerRepository.deleteAll();
        List<Customer> customers = customerRepository.findAll();

        //then
        assertThat(customers.isEmpty(), is(true));

    }
}