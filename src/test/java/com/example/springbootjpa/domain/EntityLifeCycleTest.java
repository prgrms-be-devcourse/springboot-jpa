package com.example.springbootjpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EntityLifeCycleTest {

    @Autowired
    private EntityManagerFactory emf;

    Customer customer;

    @Test
    @DisplayName("영속 상태 테스트")
    void managedTest() throws Exception {

        //given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        //when
        transaction.begin();

        customer = Customer.builder()
                .username("hong")
                .address("서울시").build();

        entityManager.persist(customer);

        //then
        assertThat(entityManager.contains(customer)).isTrue();
        transaction.commit();
    }

    @Test
    @DisplayName("준영속 상태 테스트")
    void detachedTest() throws Exception {

        //given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        //when
        transaction.begin();

        customer = Customer.builder()
                .username("hong")
                .address("서울시").build();

        entityManager.persist(customer);
        entityManager.detach(customer);

        //then
        assertThat(entityManager.contains(customer)).isFalse();
        transaction.commit();
    }

    @Test
    @DisplayName("비영속 상태 테스트")
    void removedTest() throws Exception {

        //given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        //when
        transaction.begin();

        customer = Customer.builder()
                .username("hong")
                .address("서울시").build();

        entityManager.persist(customer);
        entityManager.remove(customer);

        //then
        assertThat(entityManager.contains(customer)).isFalse();
        transaction.commit();
    }

    @Test
    @DisplayName("머지(병합) 테스트")
    void mergeTest() throws Exception {

        //given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        //when
        transaction.begin();

        customer = Customer.builder()
                .username("hong")
                .address("서울시").build();

        entityManager.persist(customer);
        entityManager.flush();
        entityManager.detach(customer);

        //then
        assertThat(entityManager.contains(customer)).isFalse();
        Customer mergeCustomer = entityManager.merge(customer);

        assertThat(entityManager.contains(mergeCustomer)).isTrue();
        transaction.commit();
    }
}
