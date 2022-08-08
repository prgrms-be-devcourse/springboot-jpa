package com.kdt.lecture.domain;

import com.kdt.lecture.Repository.OrderRepository;
import com.kdt.lecture.domain.domainV1.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
public class CustomerRepositoryTest2 {

    @Autowired
    EntityManagerFactory emf;

    private final String FIRST_NAME = "firstName";
    private final String NEW_FIRST_NAME = "newFirstName";

    @Test
    public void 영속_조회_및_수정_변경감지() throws Exception {

        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(1L, FIRST_NAME, "lastName");
        em.persist(customer);

        transaction.commit();

        //when
        customer.changeFirstName(NEW_FIRST_NAME);

        //then
        Customer entity = em.find(Customer.class, 1L);
        assertThat(entity.getFirstName()).isEqualTo(NEW_FIRST_NAME); //변경감지
    }

    @Test
    public void 준영속_변경감지_실패() throws Exception {

        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(2L, FIRST_NAME, "lastName");
        em.persist(customer);

        transaction.commit();

        //when
        transaction.begin();
        em.detach(customer);
        transaction.commit();

        customer.changeFirstName(NEW_FIRST_NAME);

        //then
        Customer entity = em.find(Customer.class, 2L);
        assertThat(entity.getFirstName()).isEqualTo(FIRST_NAME); //변경감지
    }

    @Test
    public void 삭제() throws Exception {

        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Customer customer = new Customer(3L, FIRST_NAME, "lastName");
        em.persist(customer);

        transaction.commit();

        //when
        transaction.begin();
        Customer entity = em.find(Customer.class, 3L);
        em.remove(entity);
        transaction.commit();

        //then
        assertThat(em.find(Customer.class, 3L)).isNull();
    }
}
