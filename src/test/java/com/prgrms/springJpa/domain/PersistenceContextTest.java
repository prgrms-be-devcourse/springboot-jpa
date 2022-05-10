package com.prgrms.springJpa.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersistenceContextTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EntityManagerFactory emf;

    private EntityManager entityManager;

    private EntityTransaction transaction;

    @BeforeEach
    void setUp() {
        entityManager = emf.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @DisplayName("영속화를 하면 영속성 컨텍스트에서 관리한다.")
    @Test
    void persist() {
        // given
        Customer customer = customer();

        // when
        entityManager.persist(customer);

        // then
        assertThat(entityManager.contains(customer)).isTrue();
    }

    @DisplayName("영속상태에서 준영속 상태로 변경한다.")
    @Test
    void detach() {
        // given
        Customer customer = customer();
        entityManager.persist(customer);

        // when
        entityManager.detach(customer);

        // then
        assertThat(entityManager.contains(customer)).isFalse();
    }

    @DisplayName("고객을 저장한다.")
    @Test
    void persist_commit() {
        // given
        transaction.begin();

        Customer customer = customer();
        entityManager.persist(customer);

        // when
        transaction.commit();

        // then
        Customer findCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(findCustomer).usingRecursiveComparison()
            .isEqualTo(customer);
    }

    @DisplayName("1차 캐시에서 조회한다.")
    @Test
    void find_From1thCache() {
        // given
        transaction.begin();

        Customer customer = customer();
        entityManager.persist(customer);

        transaction.commit();

        // when
        Customer findCustomer = entityManager.find(Customer.class, customer.getId());

        // then
        assertAll(
            () -> assertThat(findCustomer).isEqualTo(customer),
            () -> assertThat(entityManager.contains(customer)).isTrue()
        );
    }

    @DisplayName("1차 캐시가 아닌 DB에서 조회한다.")
    @Test
    void find_FromDataBase() {
        // given
        transaction.begin();

        Customer customer = customer();
        entityManager.persist(customer);

        transaction.commit();

        // when
        entityManager.detach(customer);

        // then
        assertThat(entityManager.contains(customer)).isFalse();

        Customer findCustomer = entityManager.find(Customer.class, customer.getId());
        assertAll(
            () -> assertThat(findCustomer).isNotEqualTo(customer),
            () -> assertThat(findCustomer).usingRecursiveComparison().isEqualTo(customer),
            () -> assertThat(entityManager.contains(findCustomer)).isTrue()
        );
    }

    @DisplayName("고객을 수정한다.")
    @Test
    void update_commit() {
        // given
        transaction.begin();

        Customer customer = customer();
        entityManager.persist(customer);

        transaction.commit();

        // when
        transaction.begin();
        customer.setFirstName("hwa");
        customer.setLastName("you");
        transaction.commit();

        // then
        Customer findCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(findCustomer).usingRecursiveComparison()
            .isEqualTo(customer);
    }

    @DisplayName("고객을 삭제한다.")
    @Test
    void remove_commit() {
        // given
        transaction.begin();

        Customer customer = customer();
        entityManager.persist(customer);

        transaction.commit();

        // when
        transaction.begin();
        entityManager.remove(customer);
        transaction.commit();

        // then
        assertAll(
            () -> assertThat(customerRepository.findById(customer.getId())).isEmpty(),
            () -> assertThat(entityManager.contains(customer)).isFalse()
        );
    }

    private Customer customer() {
        return new Customer(1L, "minhwan", "yu");
    }
}
