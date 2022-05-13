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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    @DisplayName("저장 후 영속성 관리 되는지 테스트 : 1차 캐시")
    void testSave(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("kyung-il");
        customer.setLastName("jung");

        em.persist(customer);
        assertThat(em.contains(customer)).isTrue();
        transaction.commit();
    }

    @Test
    @DisplayName("detach 후 영속성 관리가 준영속으로 되는지: DB 조회")
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

        assertThat(em.contains(customer)).isFalse();
    }

    @Test
    @DisplayName("고객 수정")
    void testUpdate(){

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        //given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("kyung-il");
        customer.setLastName("jung");

        //when
        em.persist(customer);
        customer.setLastName("j");

        transaction.commit();

        //then
        transaction.begin();
        //then
        assertThat(em.find(Customer.class, 1L).getLastName()).isEqualTo("j");
        transaction.commit();
    }

    @Test
    @DisplayName("고객 삭제")
    void testDelete(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();


        //given

        transaction.begin();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("kyung-il");
        customer.setLastName("jung");

        //when
        em.persist(customer);
        em.remove(customer);

        //then
        assertThat(em.contains(customer)).isFalse();
        assertThat(em.find(Customer.class, 1L)).isNull();

        transaction.commit();
    }

}