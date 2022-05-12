package com.example.chapter1.study;

import com.example.chapter1.domain.customer.domain.Customer;
import com.example.chapter1.domain.customer.domain.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * 미션 2 영속성 컨텍스트 학습
 */
@Slf4j
@DataJpaTest
class PersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    void 조회_DB조회() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer("guppy", "hong");

        em.persist(customer);
        transaction.commit();

        em.detach(customer); //영속 -> 준영속
//        em.clear(); //영속성 컨텍스트를 초기화 한다.

        Customer entity = em.find(Customer.class, customer.getId()); // DB 에서 조회한다. SELECT ...
        log.info("{} {}", entity.getFirstName(), entity.getLastName());
//        em.find(Customer.class, 2L); // SELECT Query 수행되지 않는다. 1차캐시 사용
    }

    @Test
    void 조회_1차캐시_이용() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Customer customer = new Customer("honggu", "kang");

        em.persist(customer);
        tx.commit();

        Customer entity = em.find(Customer.class, customer.getId()); // 1차 캐시에서 조회한다.
        log.info("{} {}", entity.getFirstName(), entity.getLastName());
    }
}
