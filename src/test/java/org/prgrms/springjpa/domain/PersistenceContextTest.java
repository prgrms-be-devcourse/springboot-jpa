package org.prgrms.springjpa.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersistenceContextTest {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void 저장() {
        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Customer customer = new Customer("woojun", "park"); //비영속 상태
        //when
        em.persist(customer); // 비영속 -> 영속 (영속화)
        transaction.commit(); //em.flush();
    }

    @Test
    void 조회_DB조회() {
        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Customer customer = new Customer("woojun", "park"); //비영속 상태
        //when
        em.persist(customer);
        transaction.commit();
        em.detach(customer); // 영속 -> 준영속
        Customer selected = em.find(Customer.class, 1L);
        //then
        assertThat(selected).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    void 조회_1차캐시_이용() {
        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Customer customer = new Customer("woojun", "park"); //비영속 상태
        //when
        em.persist(customer);
        transaction.commit();
        Customer selected = em.find(Customer.class, 1L); //1차 캐시 이용
        //then
        assertThat(selected).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    void 수정() {
        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Customer customer = new Customer("woojun", "park"); //비영속 상태
        //when
        em.persist(customer);
        transaction.commit();
//        transaction.begin();
        customer.setFirstName("gildong");
        Customer update = em.find(Customer.class, 1L);
//        transaction.commit();
        //then
    }

    @Test
    void 삭제() {
        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Customer customer = new Customer("woojun", "park"); //비영속 상태
        //when
        em.persist(customer);
        transaction.commit();
        transaction.begin();

        em.remove(customer);
        transaction.commit();
        //then
    }
}
