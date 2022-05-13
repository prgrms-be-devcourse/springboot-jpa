package com.example.springbootjpa.domain;

import com.example.springbootjpa.repository.CustomerRepository;
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
    void 저장() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Customer customer = new Customer(1L, "sanghyeok", "park"); //비영속 상태

        em.persist(customer); // 비영속 -> 영속 ( 영속화 )

        transaction.commit(); //em.flush(); db에 쿼리전송

//        em.detach(customer); // 영속 -> 준영속 (준영속화)

        Customer selected = em.find(Customer.class, 1L);
        log.info("{} {}", selected.getFirstName(), selected.getLastName());
    }

    @Test
    void 수정() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Customer customer = new Customer(1L, "sanghyeok", "park"); //비영속 상태

        em.persist(customer); // 비영속 -> 영속 ( 영속화 )

        transaction.commit(); //em.flush(); db에 쿼리전송

        transaction.begin();

        customer.setFirstName("test");
        customer.setLastName("kim");

        transaction.commit();
    }

    @Test
    void 삭제() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Customer customer = new Customer(1L, "sanghyeok", "park"); //비영속 상태

        em.persist(customer); // 비영속 -> 영속 ( 영속화 )

        transaction.commit(); //em.flush(); db에 쿼리전송

        transaction.begin();

        em.remove(customer);

        transaction.commit();
    }
}
