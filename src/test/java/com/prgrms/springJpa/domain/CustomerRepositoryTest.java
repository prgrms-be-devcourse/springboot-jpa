package com.prgrms.springJpa.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @DisplayName("고객을 저장한다.")
    @Test
    void save() {
        // given
        Customer customer = customer();

        // when
        customerRepository.save(customer);

        // then
        Customer findCustomer = customerRepository.findById(1L).get();
        assertThat(findCustomer).usingRecursiveComparison()
            .isEqualTo(customer);
    }

    @DisplayName("모든 고객을 조회한다.")
    @Test
    void findAll() {
        // given
        Customer customer = customer();
        Customer secondCustomer = new Customer(2L, "honggu", "kang");
        customerRepository.save(customer);
        customerRepository.save(secondCustomer);

        // when
        List<Customer> customers = customerRepository.findAll();

        // then
        assertThat(customers).hasSize(2);
    }

    @DisplayName("특정 id의 고객을 조회한다.")
    @Test
    void findById() {
        // given
        Customer customer = customer();
        customerRepository.save(customer);

        // when
        Customer findCustomer = customerRepository.findById(1L).get();

        // then
        assertThat(findCustomer).usingRecursiveComparison()
            .isEqualTo(customer);
    }

    @DisplayName("고객을 수정한다.")
    @Test
    @Transactional
    void update() {
        // given
        Customer customer = customer();
        customerRepository.save(customer);

        // when
        Customer findCustomer = customerRepository.findById(1L).get();
        findCustomer.setFirstName("hwa");
        findCustomer.setLastName("you");

        // then
        Customer updatedCustomer = customerRepository.findById(1L).get();
        assertThat(updatedCustomer).usingRecursiveComparison()
            .isEqualTo(findCustomer);
    }

    @DisplayName("고객을 삭제한다.")
    @Test
    void delete() {
        // given
        Customer customer = customer();
        customerRepository.save(customer);

        // when
        customerRepository.deleteById(1L);

        // then
        assertThat(customerRepository.findAll()).isEmpty();
    }

    @DisplayName("모든 고객을 삭제한다.")
    @Test
    void deleteAll() {
        // given
        Customer customer = customer();
        Customer secondCustomer = new Customer(2L, "honggu", "kang");
        customerRepository.save(customer);
        customerRepository.save(secondCustomer);

        // when
        customerRepository.deleteAll();

        // then
        assertThat(customerRepository.findAll()).isEmpty();
    }

    private Customer customer() {
        return new Customer(1L, "minhwan", "yu");
    }

}
