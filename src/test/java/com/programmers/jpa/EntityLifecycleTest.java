package com.programmers.jpa;

import com.programmers.jpa.customer.domain.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class EntityLifecycleTest {

    @Autowired
    EntityManagerFactory emf;

    private EntityManager em;

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
    }

    @Test
    void save() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Customer customer = new Customer("명한", "유");
        log.info("before persist");
        em.persist(customer);
        log.info("after persist");
        transaction.commit();

        log.info("저장 고객: {}", customer);
        em.close();
    }

    @Test
    void findUsing1stCache() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Customer customer = new Customer("명한", "유");
        em.persist(customer);
        transaction.commit();

        Customer foundCustomer = em.find(Customer.class, customer.getId());
        log.info("1차 캐쉬에서 고객 조회: {}", foundCustomer);
        em.close();

    }

    @Test
    void findInDB() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Customer customer = new Customer("명한", "유");
        em.persist(customer);
        transaction.commit();

        em.clear();
        Customer foundCustomer = em.find(Customer.class, customer.getId());
        log.info("DB에서 고객 조회: {}", foundCustomer);
        em.close();

    }

    @Test
    void update() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Customer customer = new Customer("명한", "유");
        em.persist(customer);
        transaction.commit();
        log.info("변경 전 고객 이름: {}", customer.getLastName() + customer.getFirstName());

        transaction.begin();
        customer.changeName("박", "은직");
        transaction.commit();

        log.info("변경 후 고객 이름: {}", customer.getLastName() + customer.getFirstName());
        em.close();

    }

    @Test
    void delete() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Customer customer = new Customer("명한", "유");
        em.persist(customer);
        transaction.commit();

        transaction.begin();
        em.remove(customer);
        transaction.commit();

        Customer foundCustomer = em.find(Customer.class, customer.getId());
        log.info("삭제된 고객 정보: {}", foundCustomer);
        em.close();
    }
} 
