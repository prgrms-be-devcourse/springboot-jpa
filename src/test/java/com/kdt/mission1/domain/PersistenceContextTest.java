package com.kdt.mission1.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringBootTest
@Rollback(value = false)
public class PersistenceContextTest {

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
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setFirstName("seongwon");
        customer.setLastName("choi");

        entityManager.persist(customer);
//        customer = new Customer(customer.getId(), "mm", "mm");
        customer.setFirstName("newName");
        transaction.commit();

        System.out.println("customer.getId() = " + customer.getId());
        System.out.println("FullName: " + customer.getFirstName() + customer.getLastName());
    }

    @Test
    void 조회_DB조회() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setFirstName("seongwon");
        customer.setLastName("choi");

        entityManager.persist(customer); // 비영속 -> 영속
        System.out.println("customer.getId() = " + customer.getId());
        transaction.commit(); // entityManger.flush()
        System.out.println("customer.getId() = " + customer.getId());

        entityManager.detach(customer); // 영속 -> 준영속

        Customer selected = entityManager.find(Customer.class, customer.getId());
        System.out.println("customer.getId() = " + customer.getId());
        assertSame(customer.getFirstName(), selected.getFirstName());
        assertSame(customer.getLastName(), selected.getLastName());

    }

    @Test
    void 조회_1차캐시_이용() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer("seongwon", "choi"); // 비영속상태

        entityManager.persist(customer);
        transaction.commit();

        Customer selected = entityManager.find(Customer.class, customer.getId());
        assertThat(selected, samePropertyValuesAs(customer));
    }

    @Test
    void 수정() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer("seongwon", "choi");

        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();
        customer.setFirstName("gupppy");
        customer.setLastName("hong");

        transaction.commit();
    }

    @Test
    void 삭제() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer("seongwon", "choi");

        entityManager.persist(customer); // 비영속 ->
        transaction.commit();

        transaction.begin();

        entityManager.remove(customer);

        transaction.commit();
    }
}
