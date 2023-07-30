package com.example.springjpamission.customer.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
class CustomerRepositoryTest {

    static final String FIRST_NAME = "김";
    static final String LAST_NAME = "별";

    @Autowired
    CustomerRepository customerRepository;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer();
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        testCustomer = customerRepository.save(customer);
    }

    @Test
    void create() {
        //given
        Customer customer = new Customer();
        customer.setFirstName("윤");
        customer.setLastName("영운");

        //when
        Customer save = customerRepository.save(customer);

        //then
        assertThat(save.getLastName()).isEqualTo("영운");
    }

    @Test
    void findById() {
        //when
        Customer savedCustomer = customerRepository.findById(testCustomer.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원 입니다."));

        //then
        assertThat(savedCustomer.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(savedCustomer.getLastName()).isEqualTo(LAST_NAME);
    }

    @Test
    void deleteById(){
        //given
        Customer savedCustomer = customerRepository.findById(testCustomer.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원 입니다."));
        Long savedId = savedCustomer.getId();

        //when
        customerRepository.deleteById(savedId);

        //then
        assertThat(customerRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    void update(){
        //given
        Customer savedCustomer = customerRepository.findById(testCustomer.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원 입니다."));

        //when
        savedCustomer.setFirstName("윤");
        savedCustomer.setLastName("영운");

        //then
        Customer updatedCustomer = customerRepository.findById(savedCustomer.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원 입니다."));
        assertThat(updatedCustomer.getFirstName()).isEqualTo("윤");
    }
}
