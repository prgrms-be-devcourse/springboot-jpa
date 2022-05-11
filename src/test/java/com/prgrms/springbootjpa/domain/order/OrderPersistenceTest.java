package com.prgrms.springbootjpa.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@DataJpaTest
public class OrderPersistenceTest {
    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("연관관계 테스트")
    void relationTest() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        tx.commit();
    }

}
