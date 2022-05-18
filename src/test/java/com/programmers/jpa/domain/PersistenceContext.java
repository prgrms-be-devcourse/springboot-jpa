package com.programmers.jpa.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@SpringBootTest
@Slf4j
public class PersistenceContext {

    @Autowired
    CustomerRepository repository;

    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void 저장() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(1L, "hanju", "lee"); // 비영속

        em.persist(customer);   // 영속화

        transaction.commit();   // 쿼리 수행, flush DB와 동기화
    }

    @Test
    void 조회_1차캐시_이용() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(1L, "hanju", "lee"); // 비영속
        em.persist(customer);   // 영속화
        transaction.commit();   // 쿼리 수행, flush DB와 동기화

        Customer entity = em.find(Customer.class, 1L);
        log.info("{} {}", entity.getName().getFirstName(), entity.getName().getLastName());
    }

    @Test
    void 조회() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(2L, "hanju", "lee"); // 비영속
        em.persist(customer);   // 영속화
        transaction.commit();   // 쿼리 수행, flush DB와 동기화

        em.clear(); // 영속성 컨텍스트 초기화

        Customer entity = em.find(Customer.class, 2L);  // DB에서 조회
        log.info("{} {}", entity.getName().getFirstName(), entity.getName().getLastName());
        em.find(Customer.class, 2L);    // 1차 캐시 사용
    }

    @Test
    void 수정() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(1L, "hanju", "lee"); // 비영속
        em.persist(customer);   // 영속화
        transaction.commit();   // flush를 통해 DB 저장

        transaction.begin();

        Customer entity = em.find(Customer.class, 1L);
        entity.getName().changeFirstName("kim");
        entity.getName().changeLastName("minji");

        transaction.commit();   // UPDATE

    }

    @Test
    void 삭제() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(1L, "hanju", "lee"); // 비영속
        em.persist(customer);   // 영속화
        transaction.commit();   // flush를 통해 DB 저장

        transaction.begin();

        Customer entity = em.find(Customer.class, 1L);
        em.remove(entity);

        transaction.commit();
    }


}
