package com.programmers.jpa.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.assertThat;

// Mission 1. 단일 엔티티를 이용한 CRUD를 구현.

@SpringBootTest
class SingleEntityTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("저장 할 수 있다.")
    void save() {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Member member = createMember();
        em.persist(member);
        transaction.commit();

        transaction.begin();
        Member retrieveMember = em.find(Member.class, member.getId());
        transaction.commit();

        assertThat(retrieveMember).usingRecursiveComparison();
    }

    @Test
    @DisplayName("Member를 조회할 수 있다.")
    void findById() {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Member member = createMember();
        em.persist(member);
        transaction.commit();

        transaction.begin();
        Member retrieveMember = em.find(Member.class, member.getId());
        transaction.commit();

        assertThat(retrieveMember).usingRecursiveComparison();
    }

    @Test
    @DisplayName("Member를 업데이트 할 수 있다.")
    void update() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Member member = createMember();
        em.persist(member);
        transaction.commit();

        transaction.begin();
        Member retrieveMember = em.find(Member.class, member.getId());
        retrieveMember.setAge(26);
        transaction.commit();

        assertThat(retrieveMember.getAge()).isEqualTo(26);
    }

    @Test
    @DisplayName("Member를 삭제할 수 있다.")
    void delete() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Member member = createMember();
        em.persist(member);
        transaction.commit();

        transaction.begin();
        em.remove(member);
        transaction.commit();

        Member retrieveMember = em.find(Member.class, member.getId());
        assertThat(retrieveMember).isNull();


    }

    private Member createMember () {
        return new Member("testId1", 25, "서울 신림", "~~" );
    }

}