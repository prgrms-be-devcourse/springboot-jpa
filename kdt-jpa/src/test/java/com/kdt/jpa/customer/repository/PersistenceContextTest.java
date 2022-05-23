package com.kdt.jpa.customer.repository;

import com.kdt.jpa.customer.domain.Customer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    void 저장() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = new Customer("YEONHO", "CHOI"); // 비영속 상태

        entityManager.persist(customer); // 비영속 -> 영속 (영속화)

        transaction.commit(); // entityManager.flush();
    }

    @Test
    void 조회_DB조회() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = new Customer("YEONHO", "CHOI"); // 비영속 상태

        entityManager.persist(customer); // 비영속 -> 영속 (영속화)

        transaction.commit(); // entityManager.flush()

        entityManager.detach(customer); // 영속 -> 준영속

        Customer selected = entityManager.find(Customer.class, 1L);
        log.info("{} {}", selected.getFirstName(), selected.getLastName());
    }

    @Test
    void 조회_1차캐시_이용() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = new Customer("YEONHO", "CHOI"); // 비영속 상태

        entityManager.persist(customer); // 비영속 -> 영속 (영속화)
        transaction.commit(); // entityManager.flush()

        Customer selected = entityManager.find(Customer.class, 1L);
        log.info("{} {}", selected.getFirstName(), selected.getLastName());
    }

    @Test
    void 수정() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = new Customer("YEONHO", "CHOI"); // 비영속 상태

        entityManager.persist(customer); // 비영속 -> 영속 (영속화)

        transaction.commit(); // entityManager.flush()

        transaction.begin();

        customer.setFirstName("Lee");
        customer.setLastName("update");

        transaction.commit(); // dirty Checking
    }

    @Test
    void 삭제() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = new Customer("YEONHO", "CHOI"); // 비영속 상태

        entityManager.persist(customer); // 비영속 -> 영속 (영속화)

        transaction.commit(); // entityManager.flush();

        transaction.begin();
        entityManager.remove(customer);
        transaction.commit();
    }
}
