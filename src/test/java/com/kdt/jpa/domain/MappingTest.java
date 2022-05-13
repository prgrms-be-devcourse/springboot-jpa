package com.kdt.jpa.domain;

import com.kdt.jpa.domain.member.Member;
import com.kdt.jpa.domain.order.Order;
import com.kdt.jpa.domain.order.OrderRepository;
import com.kdt.jpa.domain.order.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Slf4j
@SpringBootTest
@DisplayName("연관관계 매핑 테스트")
public class MappingTest {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    OrderRepository repository;

    private Member member;
    private Order order;
    private EntityManager em;
    private EntityTransaction transaction;

    @BeforeEach
    void setup() {
        member = new Member();
        member.setName("beomseok");
        member.setAddress("서울시 구로구(만) 그렇구만");
        member.setAge(26);
        member.setNickname("beomsic");
        member.setDescription("화이팅 !!!");

        order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setMemo("메모메모");
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDatetime(LocalDateTime.now());

        em = emf.createEntityManager();
        transaction = em.getTransaction();
    }
    @AfterEach
    void tearDown() {
        em.clear();
        repository.deleteAll();
    }


    @Test
    @DisplayName("단일 엔티티 매핑")
    void testInsertMember() {
        // Given

        // When
        transaction.begin();
        em.persist(member);
        transaction.commit();

        // Then
    }

    @Test
    void 양방향관계_저장테스트_편의메소드사용() {

        // Given

        // When
        transaction.begin();
        em.persist(order);

//        order.setMember(member); // 연관관계 주인
        member.addOrder(order);  // 연관관계 주인 x
        em.persist(member);
        transaction.commit();
        // Then

    }

    @Test
    void 조회_객체그래프탐색() {
        // Given

        // When
        transaction.begin();
        em.persist(order);
        member.addOrder(order);
        transaction.commit();
        em.clear();
        // Then
        Member member1 = em.find(Member.class, 1L);
        Order order1 = em.find(Order.class, member1.getOrders().get(0).getOrderId());
        assertThat(order1.getMemo().equals("메모메모"), is(true));
    }
}
