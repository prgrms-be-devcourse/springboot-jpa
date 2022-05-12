package com.blessing333.kdtjpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
@SpringBootTest
class PersistenceContextTest {
    @Autowired
    CustomerRepository repository;
    @Autowired
    EntityManagerFactory emf;

    @AfterEach
    void resetData() {
        log.info("delete all data after test");
        repository.deleteAll();
    }

    @Test
    void persistTest() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Customer customer = new Customer(1L, "Minjae", "Lee"); // 비영속상태

        em.persist(customer); // 비영속 -> 영속 (영속화)
        em.flush();

        assertThat(em.contains(customer)).isTrue();
        transaction.rollback();
    }

    @DisplayName("DB에서 데이터 조회")
    @Test
    void findFromDB() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Customer customer = new Customer(1L, "Minjae", "Lee"); // 비영속상태
        em.persist(customer); // 비영속 -> 영속 (영속화)
        transaction.commit();
        em.clear(); // 영속 -> 준영속

        Customer found = em.find(Customer.class, 1L);
        assertThat(em.contains(customer)).isFalse();
        assertThat(found.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(found.getLastName()).isEqualTo(customer.getLastName());
        assertThat(found.getCreatedAt()).isNotNull();
    }

    @DisplayName("영속성 컨텍스트에서 조회")
    @Test
    void findFromPersistenceContext() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Customer customer = new Customer(1L, "Minjae", "Lee"); // 비영속상태
        em.persist(customer); // 비영속 -> 영속 (영속화)
        transaction.commit();

        Customer found = em.find(Customer.class, customer.getId());
        assertThat(em.contains(customer)).isTrue();
        assertThat(found.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(found.getLastName()).isEqualTo(customer.getLastName());
        assertThat(found.getCreatedAt()).isNotNull();
    }

    @Test
    void deleteTest() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Customer customer = new Customer(1L, "Minjae", "Lee"); // 비영속상태
        em.persist(customer); // 비영속 -> 영속 (영속화)
        transaction.commit();

        em.remove(customer);
        assertNull(em.find(Customer.class, customer.getId()));
    }
}
