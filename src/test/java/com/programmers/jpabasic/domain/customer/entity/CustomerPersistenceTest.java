package com.programmers.jpabasic.domain.customer.entity;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.programmers.jpabasic.support.CustomerFixture;

@DataJpaTest
class CustomerPersistenceTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;
    private EntityTransaction transaction;

    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    @Test
    @DisplayName("고객 저장에 성공한다.")
    void save() {
        // given
        Customer customer = CustomerFixture.create("heebin", "kim");

        // when
        entityManager.persist(customer);
        transaction.commit();

        // then
        Customer result = entityManager.find(Customer.class, customer.getId());
        assertThat(result.getName().getFirstName()).isEqualTo("heebin");
        assertThat(result.getName().getLastName()).isEqualTo("kim");
    }

    @Test
    @DisplayName("DB로부터 고객 조회에 성공한다.")
    void findFromDB() {
        // given
        Customer customer = CustomerFixture.create("heebin", "kim");

        entityManager.persist(customer);
        transaction.commit();

        // when
        entityManager.detach(customer);
        Customer result = entityManager.find(Customer.class, customer.getId());

        // then
        assertThat(result.getName().getFirstName()).isEqualTo("heebin");
        assertThat(result.getName().getLastName()).isEqualTo("kim");
    }

    @Test
    @DisplayName("1차 캐시로부터 고객 조회에 성공한다.")
    void findFromCache() {
        // given
        Customer customer = CustomerFixture.create("heebin", "kim");

        entityManager.persist(customer);
        transaction.commit();

        // when
        Customer result = entityManager.find(Customer.class, customer.getId());

        // then
        assertThat(result.getName().getFirstName()).isEqualTo("heebin");
        assertThat(result.getName().getLastName()).isEqualTo("kim");
    }

    @Test
    @DisplayName("고객 수정에 성공한다.")
    void update() {
        // given
        Customer customer = CustomerFixture.create("heebin", "kim");

        entityManager.persist(customer);
        transaction.commit();

        // when
        transaction.begin();
        customer.updateName("희빈", "김");
        transaction.commit();

        // then
        Customer result = entityManager.find(Customer.class, customer.getId());
        assertThat(result.getName().getFirstName()).isEqualTo("희빈");
        assertThat(result.getName().getLastName()).isEqualTo("김");
    }

    @Test
    @DisplayName("고객 삭제에 성공한다.")
    void delete() {
        // given
        Customer customer = CustomerFixture.create("heebin", "kim");

        entityManager.persist(customer);
        transaction.commit();

        // when
        transaction.begin();
        entityManager.remove(customer);
        transaction.commit();

        // then
        Customer result = entityManager.find(Customer.class, customer.getId());
        assertThat(result).isNull();
    }
}
