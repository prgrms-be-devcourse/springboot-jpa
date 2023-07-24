package com.example.springbootjpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("고객 생성")
    void saveTest() throws Exception {

        //given
        Customer customer = getCustomer();

        //when
        Customer savedCustomer = customerRepository.save(customer);

        //then
        assertThat(savedCustomer.getUsername()).isEqualTo(customer.getUsername());
        assertThat(savedCustomer.getAddress()).isEqualTo(customer.getAddress());
    }

    @Test
    @DisplayName("고객 조회 단건")
    void findByIdTest() throws Exception {

        //given
        Customer customer = getCustomer();
        Customer savedCustomer = customerRepository.save(customer);

        //when
        Customer foundCustomer = customerRepository.findById(savedCustomer.getId()).get();

        //then
        assertThat(foundCustomer.getId()).isEqualTo(savedCustomer.getId());
        assertThat(foundCustomer.getUsername()).isEqualTo(savedCustomer.getUsername());
    }

    @Test
    @DisplayName("고객 전체 조회")
    void findAllTest() throws Exception {

        //given
        Customer customer = getCustomer();
        Customer savedCustomer = customerRepository.save(customer);

        //when
        List<Customer> customers = customerRepository.findAll();

        //then
        assertNotNull(customers);
        assertThat(customers.get(0).getId()).isEqualTo(savedCustomer.getId());
    }

    @Test
    @DisplayName("고객 정보 업데이트")
    void updateTest() throws Exception {

        //given
        Customer customer = getCustomer();
        Customer savedCustomer = customerRepository.save(customer);

        //when
        Customer updateCustomer = Customer.builder()
                .username("kim")
                .address("부산시")
                .build();

        savedCustomer.updateCustomer(updateCustomer);

        //then
        assertThat(savedCustomer.getUsername()).isEqualTo("kim");
        assertThat(savedCustomer.getAddress()).isEqualTo("부산시");
    }

    @Test
    @DisplayName("고객 전체 삭제")
    void deleteAllTest() throws Exception {

        //given
        Customer customer = getCustomer();
        customerRepository.save(customer);

        //when
        customerRepository.deleteAll();

        //then
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers.isEmpty()).isEqualTo(true);
        assertThat(customers.size()).isEqualTo(0);
    }

    private Customer getCustomer() {
        Customer customer = Customer.builder()
                .username("hong")
                .address("서울시").build();

        return customer;
    }
}