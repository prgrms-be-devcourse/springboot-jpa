package com.example.springboot_jpa.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setup() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("SIN");
        customer1.setLastName("LEE");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Bruce");
        customer2.setLastName("LEE");

        customerRepository.save(customer1);
        customerRepository.save(customer2);
    }

    @AfterEach
    void clear() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("엔티티가 정상적으로 insert 되었는지 확인")
    void insert() {
        // given
        Customer customer = new Customer();
        customer.setId(3L);
        customer.setFirstName("DONG JUN");
        customer.setLastName("LEE");

        // when
        customerRepository.save(customer);

        // then
        var retrievedCustomer = customerRepository.findById(3L);
        assertThat(retrievedCustomer).isNotEmpty();
        assertThat(retrievedCustomer.get()).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("전체 엔티티 객체가 정상적으로 조회되는지 확인")
    void findAll() {
        var retrievedCustomer = customerRepository.findAll();
        // then
        assertThat(retrievedCustomer.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("엔티티 객체의 수정이 정상적으로 되는지 확인")
    @Transactional
    void update() {
        Customer entity = customerRepository.findById(1L).get();
        entity.setFirstName("YU JIN");

        // then
        Customer updated = customerRepository.findById(1L).get();
        assertThat(updated).usingRecursiveComparison().isEqualTo(entity);
    }

    @Test
    @DisplayName("엔티티 객체가 정상적으로 삭제되는지 확인")
    void deleteById() {
        customerRepository.deleteById(1L);

        // then
        var customers = customerRepository.findAll();
        assertThat(customers.size()).isEqualTo(1);
    }



}