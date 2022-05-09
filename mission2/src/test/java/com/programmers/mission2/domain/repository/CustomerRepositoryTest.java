package com.programmers.mission2.domain.repository;

import com.programmers.mission2.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EntityManagerFactory emf;


    @BeforeEach
    void tearDown(){
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("저장 테스트")
    void testSave(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("kyung-il");
        customer.setLastName("jung");

        em.persist(customer);
        transaction.commit();
    }

    @Test
    @DisplayName("조회 DB조회")
    void testReadInDB(){

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("kyung-il");
        customer.setLastName("jung");

        em.persist(customer);
        transaction.commit();


        em.detach(customer);

        Customer retrievedCustomer = em.find(Customer.class,1L);
    }

    @Test
    @DisplayName("조회: 1차 캐시 이용")
    void testReadInCache(){

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("kyung-il");
        customer.setLastName("jung");

        em.persist(customer);
        transaction.commit();

        Customer retrievedCustomer = em.find(Customer.class,1L);
    }

    @Test
    @DisplayName("고객 수정")
    void testUpdate(){

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("kyung-il");
        customer.setLastName("jung");

        em.persist(customer);
        transaction.commit();

        transaction.begin();

        customer.setFirstName("Kyung-ily");
        customer.setLastName("j");

        transaction.commit();

    }

    @Test
    @DisplayName("고객 삭제")
    void testDelete(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("kyung-il");
        customer.setLastName("jung");

        em.persist(customer);
        transaction.commit();

        transaction.begin();
        Customer retrievedCustomer = em.find(Customer.class,1L);
        em.remove(retrievedCustomer);
        transaction.commit();
    }

}