package com.kdt.jpapractice.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    Customer customer;

    @AfterEach
    void cleanData() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("고객 정보를 조회할 수 있다.")
    void select_test() {
        // given
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("eunbi");
        customer.setLastName("choi");
        repository.save(customer);

        // when
        Customer selectedCustomer = repository.findById(customer.getId()).get();

        // then
        assertThat(selectedCustomer)
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("고객 정보를 저장할 수 있다.")
    void insert_test() {
        // given
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("eunbi");
        customer.setLastName("choi");

        // when
        Customer insertedCustomer = repository.save(customer);

        // then
        assertThat(insertedCustomer)
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }


    @Test
    @DisplayName("고객 정보를 수정할 수 있다.")
    void update_test() {
        // given
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("eunbi");
        customer.setLastName("choi");
        repository.save(customer);

        // when
        Customer entity = repository.findById(customer.getId()).get();
        entity.setFirstName("Hong");

        assertThat(entity.getFirstName()).isEqualTo("Hong");
    }

    @Test
    @DisplayName("고객 정보를 삭제할 수 있다.")
    void delete_test() {
        // given
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("eunbi");
        customer.setLastName("choi");
        repository.save(customer);

        // when
        repository.delete(customer);

        // then
        assertThat(repository.findById(1L)).isEmpty();
    }
}