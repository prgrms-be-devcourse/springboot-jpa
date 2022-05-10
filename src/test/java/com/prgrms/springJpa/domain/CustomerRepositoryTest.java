package com.prgrms.springJpa.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
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
        Customer savedCustomer = customerRepository.save(customer);

        // then
        assertThat(savedCustomer).usingRecursiveComparison()
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
        Customer customer = customerRepository.save(customer());

        // when
        Customer findCustomer = customerRepository.findById(customer.getId()).get();

        // then
        assertThat(findCustomer).usingRecursiveComparison()
            .isEqualTo(customer);
    }

    @DisplayName("고객을 수정한다.")
    @Test
    void update() {
        // given
        Customer customer = customerRepository.save(customer());

        // when
        Customer findCustomer = customerRepository.findById(customer.getId()).get();
        findCustomer.setFirstName("hwa");
        findCustomer.setLastName("you");

        // then
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer).usingRecursiveComparison()
            .isEqualTo(findCustomer);
    }

    @DisplayName("고객을 삭제한다.")
    @Test
    void delete() {
        // given
        Customer customer = customerRepository.save(customer());

        // when
        customerRepository.deleteById(customer.getId());

        // then
        assertThat(customerRepository.findAll()).isEmpty();
    }

    @DisplayName("모든 고객을 삭제한다.")
    @Test
    void deleteAll() {
        // given
        customerRepository.save(customer());
        customerRepository.save(new Customer(2L, "honggu", "kang"));

        // when
        customerRepository.deleteAll();

        // then
        assertThat(customerRepository.count()).isEqualTo(0);
    }

    private Customer customer() {
        return new Customer(1L, "minhwan", "yu");
    }

}
