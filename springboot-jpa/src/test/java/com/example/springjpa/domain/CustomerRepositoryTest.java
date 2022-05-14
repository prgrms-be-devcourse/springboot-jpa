package com.example.springjpa.domain;

import com.example.springjpa.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private EntityManager entityManager;

    private Customer basicCustomer;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer("kim", "bob");
        basicCustomer = repository.save(customer);
    }

    @Test
    @DisplayName("고객정보가 저장되어야 한다.")
    void testSave() throws Exception {
        //given
        Customer customer = new Customer("taesan", "kang");
        //when
        Customer savedEntity = repository.save(customer);
        //then
        assertAll(
                // () -> assertThat(customer == savedEntity).isFalse(), // 두 객체의 참조가 다름.
                () -> assertThat(savedEntity.getId()).isEqualTo(customer.getId()),
                () -> assertThat(entityManager.contains(savedEntity)).isTrue()
        );
    }

    @Test
    @DisplayName("고객정보를 ID로 조회할 수 있다.")
    void testFind() {
        // when
        Optional<Customer> findEntity = repository.findById(basicCustomer.getId());

        // then
        assertAll(
                () -> assertThat(findEntity.isEmpty()).isFalse(),
                () -> assertThat(findEntity.get().getId()).isEqualTo(basicCustomer.getId())
        );
    }

    @Test
    @DisplayName("고객 리스트를 조회할 수 있다.")
    void testFindAll() {
        // when
        List<Customer> customers = repository.findAll();

        // then
        assertAll(
                () -> assertThat(customers.isEmpty()).isFalse(),
                () -> assertThat(customers.size()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("고객정보를 수정할 수 있다.")
    void testUpdate() throws Exception {
        //when
        basicCustomer.changeFirstName("big");
        basicCustomer.changeLastName("mountain");
        Customer findEntity = repository.findById(basicCustomer.getId()).get();

        //then
        assertAll(
                () -> assertThat(basicCustomer.getFirstName()).isEqualTo(findEntity.getFirstName()),
                () -> assertThat(basicCustomer.getLastName()).isEqualTo(findEntity.getLastName())
        );
    }

    @Test
    @DisplayName("고객을 삭제할 수 있다.")
    void testDelete() {
        // when
        repository.deleteById(basicCustomer.getId());

        Optional<Customer> findEntity = repository.findById(basicCustomer.getId());
        // then
        assertAll(
                () -> assertThat(findEntity.isEmpty()).isTrue()
        );
    }

}