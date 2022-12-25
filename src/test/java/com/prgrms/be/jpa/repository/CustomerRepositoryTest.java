package com.prgrms.be.jpa.repository;

import com.prgrms.be.jpa.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
@DataJpaTest
class CustomerRepositoryTest {

    private static final String FIRST = "수영";
    private static final String LAST = "이";
    private static final Long ID = 1L;
    @Autowired
    CustomerRepository repository;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer(ID, FIRST, LAST);
    }

    @Test
    @DisplayName("고객 정보를 삽입할 수 있다.")
    void insert_test() {
        // when
        repository.save(customer);

        // then
        Optional<Customer> entity = repository.findById(1L);
        assertThat(entity.isPresent(), is(true));
        assertThat(entity.get().getFirstName(), is(FIRST));
        assertThat(entity.get().getLastName(), is(LAST));
    }

    @Test
    @DisplayName("고객 정보를 조회할 수 있다.")
    void find_test() {
        // given
        repository.save(customer);

        // when
        Optional<Customer> entity = repository.findById(1L);

        // then
        assertThat(entity.isPresent(), is(true));
        assertThat(entity.get().getFirstName(), is(FIRST));
        assertThat(entity.get().getLastName(), is(LAST));
    }

    @Test
    @DisplayName("고객 정보를 수정할 수 있다.")
    void update_test() {
        // given
        String chgFirst = "태현";
        String chgLast = "공";
        repository.save(customer);

        // when
        Optional<Customer> optionalCustomer = repository.findById(1L);
        assertThat(optionalCustomer.isPresent(), is(true));

        Customer entity = optionalCustomer.get();
        entity.changeName(chgFirst, chgLast);

        // then
        Optional<Customer> updateEntity = repository.findById(1L);
        assertThat(updateEntity.isPresent(), is(true));
        assertThat(updateEntity.get().getFirstName(), is(chgFirst));
        assertThat(updateEntity.get().getLastName(), is(chgLast));

    }

    @Test
    @DisplayName("고객 정보를 삭제할 수 있다.")
    void delete_test() {
        // given
        repository.save(customer);

        // when
        repository.deleteById(1L);

        // then
        Optional<Customer> entity = repository.findById(1L);
        assertThat(entity.isPresent(), is(false));
    }
}