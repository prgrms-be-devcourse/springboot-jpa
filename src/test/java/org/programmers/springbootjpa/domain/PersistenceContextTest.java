package org.programmers.springbootjpa.domain;

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
        EntityTransaction tx = em.getTransaction();

        Customer customer = new Customer();     //비영속 상태
        customer.setId(1L);
        customer.setLastName("honggu");
        customer.setFirstName("Kang");

        em.persist(customer);   //비영속 -> 영속
        tx.commit();    //em.flush();
    }

    @Test
    void 조회_DB조회() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        Customer customer = persistCustomer(tx, em);

        em.detach(customer);    //영속 -> 준영속

        Customer found = em.find(Customer.class, 1L);
        log.info("{} {}", found.getFirstName(), found.getLastName());
    }

    @Test
    void 조회_1차캐시_이용() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        persistCustomer(tx, em);

        Customer found = em.find(Customer.class, 1L);
        log.info("{} {}", found.getFirstName(), found.getLastName());
    }

    @Test
    void 수정() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        Customer customer = persistCustomer(tx, em);

        tx.begin();
        customer.setLastName("hong");
        customer.setFirstName("guppy");
        tx.commit();
    }

    @Test
    void 삭제() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        Customer customer = persistCustomer(tx, em);

        tx.begin();

        em.remove(customer);

        tx.commit();
    }

    private Customer persistCustomer(EntityTransaction tx, EntityManager em) {
        tx.begin();

        Customer customer = new Customer();     //비영속 상태
        customer.setId(1L);
        customer.setLastName("honggu");
        customer.setFirstName("Kang");

        em.persist(customer);   //비영속 -> 영속
        tx.commit();    //em.flush();
        return customer;
    }
}
