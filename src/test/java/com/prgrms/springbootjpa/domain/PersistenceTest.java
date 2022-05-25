package com.prgrms.springbootjpa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@DataJpaTest
public class PersistenceTest {
    @Autowired
    EntityManagerFactory emf;

    private EntityManager em;
    private EntityTransaction tx;

    @BeforeEach
    void setUp() {
        em  = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();
    }

    @AfterEach
    void clear() {
        tx.commit();
    }

    @Test
    void testManaged() {
         // given
        Customer customer = new Customer("jerry", "hong");

         // when
         em.persist(customer);

         //then
         assertThat(em.contains(customer)).isTrue();
    }

    @Test
    void testDetached() {
        // given
        Customer customer = new Customer("jerry", "hong");
        em.persist(customer);

        // when
        em.detach(customer);

        // then
        assertThat(em.contains(customer)).isFalse();
    }

    @Test
    void testDetachedByClear() {
        // given
        Customer customer = new Customer("jerry", "hong");
        em.persist(customer);

        // when
        em.clear();

        // then
        assertThat(em.contains(customer)).isFalse();
    }


    @Test
    void testRemoved() {
        // given
        Customer customer = new Customer("jerry", "hong");
        em.persist(customer);

        // when
        em.remove(customer);

        // then
        assertThat(em.contains(customer)).isFalse();
        assertThat(em.find(Customer.class, 1L)).isNull();
    }
}
