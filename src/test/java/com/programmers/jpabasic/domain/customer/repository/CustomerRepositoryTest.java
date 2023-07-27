package com.programmers.jpabasic.domain.customer.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.programmers.jpabasic.domain.customer.entity.Customer;
import com.programmers.jpabasic.support.CustomerFixture;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("고객 저장에 성공한다.")
    void save() {
        // given
        Customer customer = CustomerFixture.create("heebin", "kim");

        // when
        customerRepository.save(customer);

        // then
        Customer result = customerRepository.findById(customer.getId()).orElseThrow();
        assertThat(result.getName().getFirstName()).isEqualTo("heebin");
        assertThat(result.getName().getLastName()).isEqualTo("kim");
    }

    @Test
    @DisplayName("모든 고객 조회에 성공한다.")
    void findAll() {
        // given
        Customer customer1 = CustomerFixture.create("heebin", "kim");
        Customer customer2 = CustomerFixture.create("희빈", "김");

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        // when
        List<Customer> result = customerRepository.findAll();

        // then
        assertThat(result).hasSize(2).contains(customer1, customer2);
    }

    @Test
    @DisplayName("고객 ID로 조회에 성공한다.")
    void findById() {
        // given
        Customer customer = CustomerFixture.create("heebin", "kim");
        customerRepository.save(customer);

        // when
        Customer result = customerRepository.findById(customer.getId()).orElseThrow();

        // then
        assertThat(result.getName().getFirstName()).isEqualTo("heebin");
        assertThat(result.getName().getLastName()).isEqualTo("kim");
    }

    @Test
    @DisplayName("고객 수정에 성공한다.")
    void update() {
        // given
        Customer customer = CustomerFixture.create("heebin", "kim");
        customerRepository.save(customer);

        // when
        customer.updateName("희빈", "김");

        // then
        Customer result = customerRepository.findById(customer.getId()).orElseThrow();
        assertThat(result.getName().getFirstName()).isEqualTo("희빈");
        assertThat(result.getName().getLastName()).isEqualTo("김");
    }

    @Test
    @DisplayName("고객 삭제에 성공한다.")
    void delete() {
        // given
        Customer customer = CustomerFixture.create("heebin", "kim");
        customerRepository.save(customer);

        // when
        customerRepository.delete(customer);

        // then
        assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }
}
