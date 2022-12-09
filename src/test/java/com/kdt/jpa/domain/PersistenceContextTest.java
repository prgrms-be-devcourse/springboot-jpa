package com.kdt.jpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Slf4j
@SpringBootTest
public class PersistenceContextTest {

    @Autowired
    CustomerRepository repository;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void 저장() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("taehee");
        customer.setLastName("kim");

        entityManager.persist(customer);
        transaction.commit();
    }

    @Test
    void 조회_DB조회() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("taehee");
        customer.setLastName("kim");

        entityManager.persist(customer);
        transaction.commit();

        entityManager.detach(customer);

        Customer selected = entityManager.find(Customer.class, 1L);
        log.info("{} {}", selected.getFirstName(), selected.getLastName());
    }

    @Test
    void 조회_1차캐시_이용() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("taehee");
        customer.setLastName("kim");

        entityManager.persist(customer);
        transaction.commit();

        Customer selected = entityManager.find(Customer.class, 1L);
        log.info("{} {}", selected.getFirstName(), selected.getLastName());
    }

    @Test
    void 수정() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("taehee");
        customer.setLastName("kim");

        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();
        customer.setFirstName("minji");
        customer.setLastName("kim");

        transaction.commit();
    }

    @Test
    void 삭제() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("taehee");
        customer.setLastName("kim");

        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();

        entityManager.remove(customer);

        transaction.commit();
    }

}
