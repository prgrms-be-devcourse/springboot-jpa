package com.prgms.springbootjpa.mission2;

import com.prgms.springbootjpa.domain.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EntityManagerTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("영속 상태로 만든다.")
    void 영속() {
        //given
        EntityManager em = emf.createEntityManager();

        //when
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Customer customer = new Customer("길동", "홍");
        em.persist(customer);
        transaction.commit();

        //then
        boolean contains = em.contains(customer);
        Assertions.assertThat(contains)
            .isTrue();
    }

    @Test
    @DisplayName("비영속 상태로 만든다.")
    void 비영속() {
        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        //when
        transaction.begin();
        Customer customer = new Customer("길동", "홍");
        transaction.commit();

        //then
        boolean contains = em.contains(customer);
        Assertions.assertThat(contains)
            .isFalse();
    }

    @Test
    @DisplayName("준영속 상태로 만든다.")
    void 준영속() {
        //given
        EntityManager em = emf.createEntityManager();
        var transaction = em.getTransaction();

        //when
        transaction.begin();
        Customer customer = new Customer("길동", "홍");
        em.persist(customer);
        transaction.commit();
        em.detach(customer);

        //then
        boolean contains = em.contains(customer);
        Assertions.assertThat(contains)
            .isFalse();
    }

    @Test
    @DisplayName("영속성 컨텍스트에서 삭제 한다.")
    void 삭제() {
        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        //when
        transaction.begin();
        Customer customer = new Customer("길동", "홍");
        em.persist(customer);
        em.remove(customer);
        transaction.commit();

        //then
        boolean contains = em.contains(customer);
        Assertions.assertThat(contains)
            .isFalse();
    }

}
