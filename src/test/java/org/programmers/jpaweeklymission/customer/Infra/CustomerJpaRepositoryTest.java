package org.programmers.jpaweeklymission.customer.Infra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.jpaweeklymission.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerJpaRepositoryTest {
    @Autowired
    private CustomerJpaRepository repository;
    @Autowired
    private TestEntityManager tem;
    private Customer saved;

    @BeforeEach
    void setup() {
        Customer customer = Customer.builder()
                .firstName("길동")
                .lastName("홍")
                .build();
        saved = repository.save(customer);
        tem.clear();
    }

    @Test
    @DisplayName("고객을 저장할 수 있다.")
    void testSave() {
        // given
        // when
        // then
        assertThat(saved).isNotNull();
    }

    @Test
    @DisplayName("고객을 아이디로 조회할 수 있다.")
    void testFindById() {
        // given
        // when
        Optional<Customer> found = repository.findById(saved.getId());
        // then
        assertThat(found).isPresent();
    }

    @Test
    @DisplayName("고객을 수정할 수 있다.")
    void testUpdate() {
        // given
        Customer forUpdate = Customer.builder()
                .firstName("상민")
                .lastName("박")
                .build();
        // when
        Customer customer = repository.findById(saved.getId())
                .orElseThrow(RuntimeException::new);
        customer.changeEntity(forUpdate);
        tem.flush();
        tem.clear();
        // then
        Optional<Customer> updated = repository.findById(customer.getId());
        assertThat(updated).isPresent();
        assertThat(updated.get())
                .extracting(Customer::getFirstName, Customer::getLastName)
                .containsExactly(forUpdate.getFirstName(), forUpdate.getLastName());
    }

    @Test
    @DisplayName("고객을 삭제할 수 있다.")
    void testDelete() {
        // given
        // when
        repository.delete(saved);
        // then
        Optional<Customer> found = repository.findById(saved.getId());
        assertThat(found).isNotPresent();
    }
}
