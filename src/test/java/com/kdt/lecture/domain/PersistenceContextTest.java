package com.kdt.lecture.domain;

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
    EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void save_Test() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = new Customer();//비영속
        customer.setId(1L);
        customer.setFirstName("lee");
        customer.setLastName("sin");
        em.persist(customer); // 비영속 -> 영속(영속화)
        transaction.commit(); //em.flush();가 일어남
    }

    @Test
    void check_DB_Test() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = new Customer();//비영속
        customer.setId(1L);
        customer.setFirstName("lee");
        customer.setLastName("sin");
        em.persist(customer); // 비영속 -> 영속(영속화)
        transaction.commit(); //em.flush();가 일어남

        em.detach(customer); // 영속 -> 준영속
        Customer selected = em.find(Customer.class, 1L);
        log.info("{} {}", selected.getFirstName(), selected.getLastName());
    }

    @Test
    void check_UsingCache_Test() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = new Customer();//비영속
        customer.setId(1L);
        customer.setFirstName("lee");
        customer.setLastName("sin");
        em.persist(customer); // 비영속 -> 영속(영속화)
        transaction.commit(); //em.flush();가 일어남

        Customer selected = em.find(Customer.class, 1L);
        log.info("{} {}", selected.getFirstName(), selected.getLastName());
    }

    @Test
    void update_Test() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = new Customer();//비영속
        customer.setId(1L);
        customer.setFirstName("lee");
        customer.setLastName("sin");
        em.persist(customer); // 비영속 -> 영속(영속화)
        transaction.commit(); //em.flush();가 일어남

        transaction.begin();
        customer.setFirstName("hong");
        customer.setLastName("go");
        transaction.commit();
    }

    @Test
    void delete_Test() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = new Customer();//비영속
        customer.setId(1L);
        customer.setFirstName("lee");
        customer.setLastName("sin");
        em.persist(customer); // 비영속 -> 영속(영속화)
        transaction.commit(); //em.flush();가 일어남

        transaction.begin();
        em.remove(customer);
        transaction.commit();
    }
}
