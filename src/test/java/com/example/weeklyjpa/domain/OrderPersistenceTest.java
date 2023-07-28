package com.example.weeklyjpa.domain;

import com.example.weeklyjpa.domain.member.Member;
import com.example.weeklyjpa.domain.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void 양방향관계_저장() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 주문 엔티티
        Order order = new Order();
        order.setCreated_at(LocalDateTime.now());

        entityManager.persist(order);

        // 회원 엔티티
        Member member = new Member();
        member.setEmail("kanghonggu");
        member.setPassword("guppy.kang");

        // order.setMember(member); <- 연관관계 주인
        member.getOrderList().add(order); // 연관관계의 주인이 아닌곳에만 SETTING


        entityManager.persist(member);

        transaction.commit();
    }

    @Test
    void 양방향관계_저장_편의메소드() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 주문 엔티티
        Order order = new Order();
        order.setCreated_at(LocalDateTime.now());

        entityManager.persist(order);

        // 회원 엔티티
        Member member = new Member();
        member.setEmail("kanghonggu");
        member.setPassword("guppy.kang");

        member.setOrder(order); // 연관관계 편의 메소드 사용

        entityManager.persist(member);

        transaction.commit();
    }

    @Test
    void 객체그래프탐색을_이용한_조회() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 주문 엔티티
        Order order = new Order();

        entityManager.persist(order);

        // 회원 엔티티
        Member member = new Member();
        member.setEmail("kanghonggu");
        member.setPassword("guppy.kang");

        member.setOrder(order); // 연관관계 편의 메소드 사용

        entityManager.persist(member);

        transaction.commit();

        entityManager.clear();

        // 회원 조회 -> 회원의 주문 까지 조회
        Member findMember = entityManager.find(Member.class, member.getId());
//        log.info("order-memo: {}", findMember.getOrderList().get(0).getId());

        // 주문조회 -> 주문한 회원 조회
        Order findOrder = entityManager.find(Order.class, findMember.getOrderList().get(0).getId());
//        log.info("member-nickName: {}", findOrder.getMember().getEmail());
    }
}
