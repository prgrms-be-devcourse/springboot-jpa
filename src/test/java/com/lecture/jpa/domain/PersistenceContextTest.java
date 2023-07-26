package com.lecture.jpa.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        EntityManager em = emf.createEntityManager(); // 1) 엔티티 매니저 생성
        EntityTransaction transaction = em.getTransaction(); // 2) 트랜잭션 획득
        transaction.begin(); // 3) 트랙잰셕 begin

        Customer customer = new Customer(1L, "beomu", "kim"); // 4-1) 비영속
        em.persist(customer); // 4-2)영속화
        transaction.commit(); // 5) 트랜잭션 commit

        assertThat(customer, is(em.find(Customer.class, customer.getId())));
    }

    @Test
    void 조회() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Customer customer = new Customer(1L, "beomu", "kim");

        em.persist(customer);

        transaction.commit();

        em.clear(); //영속성 컨텍스트를 초기화 한다.

        Customer entity = em.find(Customer.class, 1L); // DB 에서 조회한다.
        assertThat(entity, samePropertyValuesAs(customer));
    }

    @Test
    void 조회_1차캐시_이용() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(1L, "beomu", "kim");


        em.persist(customer);

        transaction.commit();

        Customer entity = em.find(Customer.class, 1L); // 1차 캐시에서 조회한다.
        assertThat(entity, samePropertyValuesAs(customer));
    }

    @Test
    void 수정() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(1L, "beomu", "kim");

        em.persist(customer);
        transaction.commit();

        transaction.begin();

        Customer entity = em.find(Customer.class, 1L);
        entity.setFirstName("heungmin");
        entity.setLastName("son");

        transaction.commit();
        assertThat(customer, samePropertyValuesAs(em.find(Customer.class, customer.getId())));
    }

    @Test
    void 삭제() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(1L, "beomu", "kim");

        em.persist(customer);
        transaction.commit();

        transaction.begin();

        Customer entity = em.find(Customer.class, 1L);
        em.remove(entity);

        transaction.commit();
    }
}
