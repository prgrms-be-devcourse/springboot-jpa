package com.example.jpalecture.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.jpalecture.domain.order.OrderStatus.OPENED;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class OrderPersistenceTest {

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
        member.setName("kanghonggu");
        member.setNickName("guppy.kang");
        member.setAge(33);
        member.setAddress("서울시 동작구만 움직이면쏜다.");
        member.setDescription("KDT 화이팅");

        // order.setMember(member); <- 연관관계 주인
        member.getOrders().add(order); // 연관관계의 주인이 아닌곳에만 SETTING
        entityManager.persist(member);

        transaction.commit();

        entityManager.clear();

        // 연관관계의 주인이 아닌 곳에 값을 변경하였으므로 fk 설정 X
        Order findOrder = entityManager.find(Order.class, order.getUuid());
        assertThat(findOrder.getMember()).isNull();
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
        member.setName("kanghonggu");
        member.setNickName("guppy.kang2");
        member.setAge(33);
        member.setAddress("서울시 동작구만 움직이면쏜다.");
        member.setDescription("KDT 화이팅");
        member.addOrder(order); // 연관관계 편의 메소드 사용

        entityManager.persist(member);
        transaction.commit();

        entityManager.clear();
        Order findOrder = entityManager.find(Order.class, order.getUuid());
        assertThat(findOrder.getMember().getId()).isEqualTo(member.getId());
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
        member.setName("kanghonggu");
        member.setNickName("guppy.kang3");
        member.setAge(33);
        member.setAddress("서울시 동작구만 움직이면쏜다.");
        member.setDescription("KDT 화이팅");

        member.addOrder(order); // 연관관계 편의 메소드 사용
        entityManager.persist(member);

        transaction.commit();

        entityManager.clear();

        // 회원 조회 -> 회원의 주문 까지 조회
        Member findMember = entityManager.find(Member.class, 1L);
        log.info("order-memo: {}", findMember.getOrders().get(0).getMemo());

        // 주문조회 -> 주문한 회원 조회
        Order findOrder = entityManager.find(Order.class, findMember.getOrders().get(0).getUuid());
        log.info("member-nickName: {}", findOrder.getMember().getNickName());
    }

}