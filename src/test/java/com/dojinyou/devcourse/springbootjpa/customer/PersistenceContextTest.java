package com.dojinyou.devcourse.springbootjpa.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PersistenceContextTest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    CustomerRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void 조회_1차_캐시_이용() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setFirstName("dojin");
        customer.setLastName("you");

        em.persist(customer);
        transaction.commit();

        CustomerEntity entity = em.find(CustomerEntity.class, 1L);
    }

    @Test
    void 조회_DB_이용() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        CustomerEntity customer = new CustomerEntity();
        customer.setId(2L);
        customer.setFirstName("dojin");
        customer.setLastName("you");

        em.persist(customer);
        transaction.commit();

        em.clear();

        em.find(CustomerEntity.class, 2L);
    }

    @Test
    void 수정() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        CustomerEntity customer = new CustomerEntity();
        customer.setId(3L);
        customer.setFirstName("dojin");
        customer.setLastName("you");

        em.persist(customer);
        transaction.commit();

        transaction.begin();

        CustomerEntity foundEntity = em.find(CustomerEntity.class, 3L);
        foundEntity.setFirstName("update_dojin");
        foundEntity.setLastName("update_you");

        transaction.commit();
    }

    @Test
    void 삭제() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        CustomerEntity customer = new CustomerEntity();
        customer.setId(4L);
        customer.setFirstName("dojin");
        customer.setLastName("you");

        em.persist(customer);
        transaction.commit();

        transaction.begin();

        CustomerEntity foundEntity = em.find(CustomerEntity.class, 4L);
        em.remove(foundEntity);

        transaction.commit();
    }
}
