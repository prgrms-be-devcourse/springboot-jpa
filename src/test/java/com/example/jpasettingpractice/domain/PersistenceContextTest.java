package com.example.jpasettingpractice.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class PersistenceContextTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManagerFactory emf;

    EntityManager entityManager;
    EntityTransaction transaction;
    Member member;

    @BeforeEach
    void setup() {
        memberRepository.deleteAll();

        entityManager = emf.createEntityManager();
        transaction = entityManager.getTransaction();

        transaction.begin();

        member = new Member(); // 비영속 상태
        member.setName("changho");
        member.setNickName("lee");
        member.setAge(29);
        member.setAddress("daegu");
        member.setDescription("hi");

        entityManager.persist(member); // 비영속 -> 영속 (영속화)
    }

    @Test
    @DisplayName("영속 상태인 엔티티를 DB에 저장한다.")
    void SAVE_TEST() {
        transaction.commit(); // entityManager.flush()가 수행됌.

        boolean contains = entityManager.contains(member);
        assertThat(contains).isTrue();

        Optional<Member> byId = memberRepository.findById(member.getId());
        assertThat(byId).contains(member);
    }

    @Test
    @DisplayName("DB에 저장 후 엔티티를 영속 상태를 해제한 뒤 엔티티를 조회한다.")
    void FIND_BY_ID_TEST() {
        transaction.commit(); // entityManager.flush()가 수행됌.

        entityManager.detach(member); // 영속 -> 준영속
        entityManager.clear(); // 영속상태의 모든 객체를 영속성 컨텍스트에서 분리

        Member selected = entityManager.find(Member.class, member.getId());
        assertThat(selected).isEqualTo(member);
    }

    @Test
    @DisplayName("1차 캐시에 보관되어 있던 엔티티를 가져옴")
    void FIND_PRIMARY_CACHE_TEST() {
        Member selected = entityManager.find(Member.class, member.getId());
        assertThat(selected).isEqualTo(member);

        Optional<Member> byId = memberRepository.findById(selected.getId());
        assertThat(byId).isEmpty();
    }

    @Test
    @DisplayName("더티 체킹을 통해 관리 중인 엔티티가 업데이트 된다.")
    void UPDATE_ENTITY_TEST() {
        transaction.commit(); // entityManager.flush()가 수행됌.

        transaction.begin();
        member.setNickName("brandon");
        transaction.commit();

        Member selected = entityManager.find(Member.class, member.getId());
        assertThat(selected).isEqualTo(member);
    }

    @Test
    @DisplayName("엔티티 매니저에서 삭제가 된 후 commit하면 DB에서도 삭제가 된다.")
    void DELETE_ENTITY_TEST() {
        transaction.commit();

        transaction.begin();
        entityManager.remove(member);

        Optional<Member> byId = memberRepository.findById(member.getId());
        assertThat(byId).isPresent();

        transaction.commit();

        byId = memberRepository.findById(member.getId());
        assertThat(byId).isEmpty();
    }
}
