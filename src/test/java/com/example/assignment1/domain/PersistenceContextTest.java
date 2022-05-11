package com.example.assignment1.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringBootTest
public class PersistenceContextTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EntityManagerFactory emf;

    @BeforeEach
    void setup() {
        customerRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("고객 정보를 저장한다.")
    void insertTest() {
        // given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Customer customer = new Customer(1L, "Yoonoh", "Jung");

        // when
        transaction.begin();
        entityManager.persist(customer);
        transaction.commit();

        // then
        Customer findCustomer = entityManager.find(Customer.class, 1L);
        assertThat(customerRepository.count(), is(1L));
        assertThat(customer, is(findCustomer));
    }

    @Test
    @DisplayName("데이터가 영속화 되어있지 않는 경우 DB로부터 데이터를 찾아온다.")
    void findCustomerFromDatabaseTest() {
        // given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Customer customer = new Customer(1L, "Yoonoh", "Jung");

        // when
        transaction.begin();
        entityManager.persist(customer);
        transaction.commit();

        entityManager.detach(customer);

        // then
        Customer findCustomer = entityManager.find(Customer.class, 1L);
        assertThat(customer.getClass(), is(findCustomer.getClass()));
        assertThat(customer, samePropertyValuesAs(findCustomer));
        assertThat(customer, not(findCustomer));
    }

    @Test
    @DisplayName("1차 캐시로부터 가져온 데이터는 반드시 동일한 레퍼런스임을 보장한다.")
    void findCustomerFromCacheTest() {
        // given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Customer customer = new Customer(1L, "Yoonoh", "Jung");

        // when
        transaction.begin();
        entityManager.persist(customer);
        transaction.commit();


        // then
        Customer findCustomer = entityManager.find(Customer.class, 1L);
        assertThat(customer.getClass(), is(findCustomer.getClass()));
        assertThat(customer, samePropertyValuesAs(findCustomer));
        assertThat(customer, is(findCustomer));
    }

    @Test
    @DisplayName("객체의 내용이 변경되면 영속성 컨텍스트의 변경감지 기능을 통해 update 쿼리가 수행된다.")
    void updateTest() {
        // given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Customer customer = new Customer(1L, "Yoonoh", "Jung");

        // when
        transaction.begin();
        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();
        customer.updateLastName("Kim");
        transaction.commit();

        // then
        Customer findCustomer = entityManager.find(Customer.class, 1L);
        assertThat(findCustomer.getLastName(), is("Kim"));
    }

    @Test
    @DisplayName("영속성 컨텍스트에서 제거된 객체는 flush 과정에서 DB와 동기화되어 자동 삭제된다.")
    void deleteTest() {
        // given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Customer customer = new Customer(1L, "Yoonoh", "Jung");

        // when
        transaction.begin();
        entityManager.persist(customer);
        transaction.commit();

        transaction.begin();
        entityManager.remove(customer);
        transaction.commit();

        // then
        Customer findCustomer = entityManager.find(Customer.class, 1L);
        assertThat(findCustomer, is(nullValue()));
        assertThat(customerRepository.count(), is(0L));
    }
}

