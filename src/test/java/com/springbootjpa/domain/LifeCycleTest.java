package com.springbootjpa.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LifeCycleTest {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Test
    void 비영속_상태에서_영속_상태로_변경한다() {
        // given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Customer customer = new Customer("이", "근우");

        // when
        transaction.begin();
        entityManager.persist(customer);
        transaction.commit();

        // then
        assertThat(entityManager.contains(customer)).isTrue();
    }

    @Test
    void 영속_상태에서_준영속_상태로_변경한다() {
        // given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Customer customer = new Customer("이", "근우");
        transaction.begin();
        entityManager.persist(customer);

        // when
        entityManager.detach(customer);
        transaction.commit();

        // then
        assertThat(entityManager.contains(customer)).isFalse();
    }
    
    @Test
    void 준영속_상태에서_영속_상태로_변경한다() {
        // given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Customer customer = new Customer("이", "근우");
        transaction.begin();
        entityManager.persist(customer);
        entityManager.detach(customer);
        
        // when
        Customer result = entityManager.merge(customer);
        transaction.commit();
        
        // then
        assertThat(entityManager.contains(result)).isTrue();
    }
}
