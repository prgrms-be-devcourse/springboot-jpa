package com.prgrms.be.jpa.repository;

import com.prgrms.be.jpa.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
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

    private static final String FIRST = "Suyoung";
    private static final String LAST = "Lee";

    @Autowired
    CustomerRepository repository;

    @AfterEach
    void tearDown() {
        log.info("deleteAll 수행 전");
        repository.deleteAll();
        log.info("deleteAll 수행 후");
    }

    @Test
    @DisplayName("고객 정보를 삽입할 수 있다.")
    void insert_test() {
        // given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName(FIRST);
        customer.setLastName(LAST);

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
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Suyoung");
        customer.setLastName("Lee");
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
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Suyoung");
        customer.setLastName("Lee");
        repository.save(customer);

        // when
        String chgFirst = "Taehyun";
        String chgLast = "Gong";

        Optional<Customer> optionalCustomer = repository.findById(1L);
        assertThat(optionalCustomer.isPresent(), is(true));

        Customer entity = optionalCustomer.get();
        entity.setFirstName(chgFirst); // 따로 update를 하지 않아도 dirty checking 덕분에 스냅샷과 비교를 하고 달라지면 커밋이 된 후에 자동적으로 update 쿼리가 날라감.
        entity.setLastName(chgLast);

        // then
        Optional<Customer> updateEntity = repository.findById(1L);
        assertThat(updateEntity.isPresent(), is(true));
        assertThat(updateEntity.get().getFirstName(), is(chgFirst));
        assertThat(updateEntity.get().getLastName(), is(chgLast));
//        log.info("{} {}", updateEntity.getLastName(), updateEntity.getFirstName());
    }

    @Test
    @DisplayName("고객 정보를 삭제할 수 있다.")
    void delete_test() {
        // given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Suyoung");
        customer.setLastName("Lee");
        repository.save(customer);

        // when
        repository.deleteById(1L);

        // then
        Optional<Customer> entity = repository.findById(1L);
        assertThat(entity.isPresent(), is(false));
    }
}