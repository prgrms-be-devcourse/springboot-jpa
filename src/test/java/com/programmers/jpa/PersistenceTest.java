package com.programmers.jpa;

import com.programmers.jpa.domain.Member;
import com.programmers.jpa.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("영속성 컨텍스트 라이프 사이클 테스트")
public class PersistenceTest {
    @Autowired
    EntityManager em;
    @Autowired
    EntityManagerFactory emf;

    Member givenMember;

    @BeforeEach
    void init() {
        givenMember = new Member("firstName", "lastName");
    }

    @Test
    @DisplayName("엔티티 영속화")
    void persist() {
        //given
        //when
        em.persist(givenMember);

        //then
        em.flush();
        em.clear();
        Member findMember = em.find(Member.class, givenMember.getId());
        assertThat(findMember).usingRecursiveComparison().isEqualTo(givenMember);
    }

    @Test
    @DisplayName("엔티티 준영속화")
    void detach() {
        //given
        em.persist(givenMember);

        //when
        em.detach(givenMember);

        //then
        givenMember.update("newName", "newName");
        Member findMember = em.find(Member.class, givenMember.getId());
        assertThat(findMember.getFirstName()).isNotEqualTo(givenMember.getFirstName());
        assertThat(findMember.getLastName()).isNotEqualTo(givenMember.getLastName());
    }

    @Test
    @DisplayName("엔티티 병합")
    void merge() {
        //given
        em.persist(givenMember);
        em.flush();
        em.clear();

        //when
        Member mergedMember = em.merge(givenMember);

        //then
        assertThat(em.contains(givenMember)).isFalse();
        assertThat(em.contains(mergedMember)).isTrue();
    }

    @Test
    @DisplayName("엔티티 병합 - 준영속 엔티티 업데이트")
    void merge_update() {
        //given
        em.persist(givenMember);
        em.flush();
        em.clear();

        //when
        givenMember.update("newFirstName", "newLastName");
        Member mergedMember = em.merge(givenMember);

        //then
        assertThat(mergedMember.getFirstName()).isEqualTo("newFirstName");
        assertThat(mergedMember.getLastName()).isEqualTo("newLastName");
    }

    @Test
    @DisplayName("엔티티 삭제")
    void remove() {
        //given
        em.persist(givenMember);

        //when
        em.remove(givenMember);

        //then
        Member member = em.find(Member.class, givenMember.getId());
        assertThat(member).isNull();
    }
}
