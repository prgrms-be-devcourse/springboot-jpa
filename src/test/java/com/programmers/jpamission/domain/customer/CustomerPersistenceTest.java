package com.programmers.jpamission.domain.customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerPersistenceTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @DisplayName("영속성 컨택스트 - 고객 등록")
    @Test
    void persistence_customer_create() {
        // given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        // when
        transaction.begin();

        Customer customer = new Customer("재윤", "신");
        entityManager.persist(customer);

        // then
        assertThat(entityManager.contains(customer)).isTrue();
        transaction.commit();
    }
}
