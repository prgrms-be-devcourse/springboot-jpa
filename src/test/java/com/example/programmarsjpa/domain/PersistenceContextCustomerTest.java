package com.example.programmarsjpa.domain;

import com.example.programmarsjpa.domain.customer.Customer;
import com.example.programmarsjpa.domain.customer.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersistenceContextCustomerTest {

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

        Customer customer = Customer.builder()
                .firstName("so")
                .lastName("seungsoo")
                .build();

        entityManager.persist(customer);
        transaction.commit();
    }

    @Test
    void 조회_DB조회() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = Customer.builder()
                .firstName("so")
                .lastName("seungsoo")
                .build();

        entityManager.persist(customer); // 비영속 -> 영속 (영속화)
        transaction.commit();

        entityManager.detach(customer); // 영속 -> 준영속

        Customer selected = entityManager.find(Customer.class, 1L);
    }

    @Test
    void 조회_1차캐시_이용() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = Customer.builder()
                .firstName("so")
                .lastName("seungsoo")
                .build();

        entityManager.persist(customer); // 비영속 -> 영속 (영속화)
        transaction.commit(); // entityManager.flush();

        Customer selected = entityManager.find(Customer.class, 1L);
    }

    @Test
    void 수정() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = Customer.builder()
                .firstName("so")
                .lastName("seungsoo")
                .build();

        entityManager.persist(customer); // 비영속 -> 영속 (영속화)
        transaction.commit(); // entityManager.flush();

        transaction.begin();
        customer.setFirstName("kim");
        customer.setLastName("rabbit");

        transaction.commit();
    }

    @Test
    void 삭제() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = Customer.builder()
                .firstName("so")
                .lastName("seungsoo")
                .build();

        entityManager.persist(customer); // 비영속 -> 영속 (영속화)
        transaction.commit(); // entityManager.flush();

        transaction.begin();

        entityManager.remove(customer);

        transaction.commit();
    }
}
