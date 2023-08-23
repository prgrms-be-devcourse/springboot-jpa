package com.blackdog.springbootjpa;

import com.blackdog.springbootjpa.domain.customer.model.Customer;
import com.blackdog.springbootjpa.domain.customer.vo.Age;
import com.blackdog.springbootjpa.domain.customer.vo.CustomerName;
import com.blackdog.springbootjpa.domain.customer.vo.Email;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersistenceTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    @DisplayName("고객을 비영속상태로 만든다.")
    void new_NonPersistObject_NonPersistObject() {
        // given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Customer customer = Customer.builder()
                .customerName(new CustomerName("영속이"))
                .age(new Age(12))
                .email(new Email("persist@naver.com"))
                .build();

        // when
        boolean isPersist = entityManager.contains(customer);

        // then
        assertThat(isPersist).isFalse();
    }

    @Test
    @DisplayName("고객을 영속상태로 만든다.")
    void persist_NonPersistObject_PersistObject() {
        //given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = Customer.builder()
                .customerName(new CustomerName("영속이"))
                .age(new Age(12))
                .email(new Email("persist@naver.com"))
                .build();

        //when
        entityManager.persist(customer);
        transaction.commit();

        //then
        boolean isPersist = entityManager.contains(customer);

        assertThat(isPersist).isTrue();
    }

    @Test
    @DisplayName("고객을 준영속상태로 만든다.")
    void detach_PersistObject_DetachedObject() {
        // given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = Customer.builder()
                .customerName(new CustomerName("영속이"))
                .age(new Age(12))
                .email(new Email("persist@naver.com"))
                .build();
        entityManager.persist(customer);
        transaction.commit();

        // when
        entityManager.detach(customer);

        // then
        boolean isPersist = entityManager.contains(customer);

        assertThat(isPersist).isFalse();
    }

    @Test
    @DisplayName("모든 고객을 준영속상태로 만든다.")
    void clear_PersistObject_DetachedObject() {
        // given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = Customer.builder()
                .customerName(new CustomerName("영속이"))
                .age(new Age(12))
                .email(new Email("persist@naver.com"))
                .build();

        entityManager.persist(customer);
        transaction.commit();

        // when
        entityManager.clear();

        //then
        boolean isPersist = entityManager.contains(customer);

        assertThat(isPersist).isFalse();
    }

    @Test
    @DisplayName("고객을 영속성 컨텍스트에서 삭제한다.")
    void remove_PersistObject_RemoveObject() {
        //given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Customer customer = Customer.builder()
                .customerName(new CustomerName("영속이"))
                .age(new Age(12))
                .email(new Email("persist@naver.com"))
                .build();
        entityManager.persist(customer);
        transaction.commit();

        //when
        entityManager.remove(customer);

        //then
        boolean isPersist = entityManager.contains(customer);

        assertThat(isPersist).isFalse();
    }

}
