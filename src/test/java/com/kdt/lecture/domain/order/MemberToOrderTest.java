package com.kdt.lecture.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@DataJpaTest
class MemberToOrderTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("member <-> order 저장")
    void saveTest() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        Order order = EntityRegister.order(entityManager);
        Member member = EntityRegister.member(entityManager);

        entityTransaction.commit();

    }

    @Test
    @DisplayName("member <-> order 조회")
    void findTest() {

    }

    @Test
    @DisplayName("member <-> order 업데이트")
    void updateTest() {

    }

    @Test
    @DisplayName("member <-> order 삭제")
    void deleteTest() {

    }
}