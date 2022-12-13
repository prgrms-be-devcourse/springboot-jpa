package com.kdt.lecture.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    private Customer createCustomer() {
        Customer customer = new Customer(1L, "teakseung", "lee");
        return customer;
    }

    @DisplayName("고객을 저장할 수 있다.")
    @Test
    void saveTest() {
        // given
        Customer customer = createCustomer();

        // when
        repository.save(customer);

        // then
        Optional<Customer> maybeCustomer = repository.findById(1L);
        assertThat(maybeCustomer)
                .isPresent();
    }



    @DisplayName("고객을 조회할 수 있다.")
    @Test
    void findTest() {
        // given
        Customer customer = createCustomer();
        repository.save(customer);

        // when
        Optional<Customer> maybeCustomer = repository.findById(1L);

        // then
        assertThat(maybeCustomer)
                .isPresent();
    }

    @DisplayName("고객 정보를 수정할 수 있다.")
    @Test
    void updateTest() {
        // given
        Customer customer = createCustomer();
        repository.save(customer);

        // when
        Customer entity = repository.findById(1L).get();
        entity.rename("taekho", "lee");

        // then
        final String firstName = entity.getFirstName();
        final String lastName = entity.getLastName();
        assertThat(firstName)
                .isEqualTo("taekho");
        assertThat(lastName)
                .isEqualTo("lee");
    }

    @DisplayName("고객 정보를 삭제할 수 있다.")
    @Test
    void deleteTest() {
        // given
        Customer customer = createCustomer();

        // when
        repository.delete(customer);

        // then
        final Optional<Customer> maybeCustomer = repository.findById(1L);
        assertThat(maybeCustomer)
                .isEmpty();
    }
}