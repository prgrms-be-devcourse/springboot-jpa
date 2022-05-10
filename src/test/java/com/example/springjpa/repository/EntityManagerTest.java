package com.example.springjpa.repository;

import com.example.springjpa.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@SpringBootTest
@Slf4j
public class EntityManagerTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void 저장() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer();
        customer.setFirstName("geuno");
        customer.setLastName("gil");

        em.persist(customer);

        transaction.commit();
        log.info("final~~");
    }

    @Test
    void 조회_1차캐쉬_이용() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setFirstName("geuno");
        customer.setLastName("gil");

        em.persist(customer);

        transaction.commit();

        em.clear();
        Customer customer1 = em.find(Customer.class, 1L);

        Assertions.assertThat(em.contains(customer1)).isTrue();
        log.info("use query");
        em.find(Customer.class, 1L);
        log.info("no query");
    }

    @Test
    void 수정() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setFirstName("geuno");
        customer.setLastName("gil");

        em.persist(customer);

        transaction.commit();

//        transaction.begin();
        Customer customer1 = em.find(Customer.class, 1L);
        em.remove(customer1);

//        transaction.commit();

        log.info("{} {}", customer1.getFirstName(), customer1.getLastName());

    }
}
