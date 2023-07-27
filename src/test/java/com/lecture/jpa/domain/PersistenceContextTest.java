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

    private EntityManager em;

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        repository.deleteAll();
    }

    @Test
    void 저장() {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(1L, "beomu", "kim"); // 4-1) 비영속
        em.persist(customer);
        transaction.commit();

        System.out.println(customer);
        System.out.println(em.find(Customer.class, customer.getId()));
        assertThat(customer, is(em.find(Customer.class, customer.getId())));
    }

    @Test
    void 조회() {
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
