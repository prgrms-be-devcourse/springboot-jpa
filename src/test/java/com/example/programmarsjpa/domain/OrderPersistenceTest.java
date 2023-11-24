package com.example.programmarsjpa.domain;

import com.example.programmarsjpa.domain.order.Member;
import com.example.programmarsjpa.domain.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.programmarsjpa.domain.order.OrderStatus.OPENED;

@Transactional
@SpringBootTest
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
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("부재시 전화주세요.");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OPENED);

        entityManager.persist(order);

        // 회원 엔티티
        Member member = new Member();
        member.setName("seungsoo");
        member.setNickName("voidmelody");
        member.setAge(33);
        member.setAddress("길구봉구 이별");
        member.setDescription("좋아하시나요");

        member.getOrders().add(order); // 연관관계의 주인이 아닌곳에만 SETTING

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
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("부재시 전화주세요.");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OPENED);

        entityManager.persist(order);

        // 회원 엔티티
        Member member = new Member();
        member.setName("seungsoo");
        member.setNickName("voidmelody");
        member.setAge(33);
        member.setAddress("길구봉구 이별");
        member.setDescription("좋아하시나요");

        member.addOrder(order); // 연관관계 편의 메소드 사용

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
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("부재시 전화주세요.");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OPENED);

        entityManager.persist(order);

        // 회원 엔티티
        Member member = new Member();
        member.setName("seungsoo");
        member.setNickName("voidmelody");
        member.setAge(33);
        member.setAddress("길구봉구 이별");
        member.setDescription("좋아하시나요");

        member.addOrder(order); // 연관관계 편의 메소드 사용

        entityManager.persist(member);

        transaction.commit();

        entityManager.clear();

        // 회원 조회 -> 회원의 주문 까지 조회
        Member findMember = entityManager.find(Member.class, 1L);

        // 주문조회 -> 주문한 회원 조회
        Order findOrder = entityManager.find(Order.class, findMember.getOrders().get(0).getUuid());
    }
}
