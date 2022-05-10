package com.example.springjpa.domain;

import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private EntityManager entityManager;

    private Customer basicCustomer;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer(1L, "kim", "bob");
        basicCustomer = repository.save(customer);
    }

    @Test
    @Description("고객정보가 저장되어야 한다.")
    void testSave() throws Exception {
        //given
        Customer customer = new Customer(2L, "taesan", "kang");
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
    @Description("고객정보를 ID로 조회할 수 있다.")
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
    @Description("고객 리스트를 조회할 수 있다.")
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
    @Description("고객정보를 수정할 수 있다.")
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
    @Description("고객을 삭제할 수 있다.")
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