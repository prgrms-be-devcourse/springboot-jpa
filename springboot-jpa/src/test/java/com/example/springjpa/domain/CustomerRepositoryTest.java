package com.example.springjpa.domain;

import jdk.jfr.Description;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("kim");
        customer.setLastName("bob");
        basicCustomer = repository.save(customer);
    }

    @Test
    @Description("고객정보가 저장되어야 한다.")
    void testSave() throws Exception {
        //given
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("taesan");
        customer.setLastName("kang");
        //when
        Customer savedEntity = repository.save(customer);
        //then
        assertAll(
                () -> assertThat(savedEntity.getId()).isEqualTo(customer.getId()),
                () -> assertThat(customer == savedEntity).isFalse(), // 두 객체의 참조가 다름.
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
        basicCustomer.setFirstName("big");
        basicCustomer.setLastName("mountain");
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