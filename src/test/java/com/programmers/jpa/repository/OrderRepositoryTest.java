package com.programmers.jpa.repository;

import com.programmers.jpa.domain.Member;
import com.programmers.jpa.domain.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnitUtil;
import jakarta.persistence.PersistenceUtil;
import org.assertj.core.api.Assertions;
import org.hibernate.jpa.internal.PersistenceUnitUtilImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;
    @Autowired
    EntityManagerFactory emf;

    Member givenMember;

    @BeforeEach
    void setUp() {
        givenMember = new Member("firstName", "lastName");
        memberRepository.save(givenMember);
    }

    @Test
    @DisplayName("성공: 다대일 단방향 lazy loading 테스트")
    void manyToOne_lazyLoading() {
        //given
        Order order = new Order("memo", givenMember);
        orderRepository.save(order);
        em.flush();
        em.clear();

        //when
        Order findOrder = orderRepository.findById(order.getId()).get();

        //then
        boolean isLoaded = emf.getPersistenceUnitUtil().isLoaded(findOrder.getMember());
        assertThat(isLoaded).isFalse();
    }

    @Test
    @DisplayName("성공: 다대일 단방향 fetch join 테스트")
    void manyToOne_lazyLoading_fetchJoin() {
        //given
        Order order = new Order("memo", givenMember);
        orderRepository.save(order);
        em.flush();
        em.clear();

        //when
        Order findOrder = orderRepository.findByIdWithMember(order.getId()).get();

        //then
        boolean isLoaded = emf.getPersistenceUnitUtil().isLoaded(findOrder.getMember());
        assertThat(isLoaded).isTrue();
    }
}