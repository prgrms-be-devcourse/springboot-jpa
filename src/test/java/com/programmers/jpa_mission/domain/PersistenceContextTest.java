package com.programmers.jpa_mission.domain;

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

        Customer customer = new Customer("BeomBhul", "Shin");

        entityManager.persist(customer);
        transaction.commit();
    }

    @Test
   	void 조회_1차캐시_이용() {
   		EntityManager entityManager = emf.createEntityManager();
   		EntityTransaction transaction = entityManager.getTransaction();

   		transaction.begin();

   		Customer customer = new Customer("BeomBhul", "Shin");

   		entityManager.persist(customer);
   		transaction.commit();

   		Customer selected = entityManager.find(Customer.class, 1L);
        log.info("{} {}", selected.getFirstName(), selected.getLastName());
   	}

    @Test
   	void 조회_DB_이용() {
   		EntityManager entityManager = emf.createEntityManager();
   		EntityTransaction transaction = entityManager.getTransaction();

   		transaction.begin();

   		Customer customer = new Customer("BeomBhul", "Shin");

   		entityManager.persist(customer);
   		transaction.commit();

        entityManager.detach(customer);
        Customer selected = entityManager.find(Customer.class, 1L);
        log.info("{} {}", selected.getFirstName(), selected.getLastName());
   	}

    @Test
    void 수정() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer("BeomBhul", "Shin");

        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();

        customer.update("Beombu");
        transaction.commit();
    }

    @Test
    void 삭제() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer("BeomBhul", "Shin");

        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();

        Customer selected = entityManager.find(Customer.class, 1L);
        entityManager.remove(selected);

        transaction.commit();
    }
}
