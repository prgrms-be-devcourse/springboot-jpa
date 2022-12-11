package com.example.springbootpart4.domain.order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.springbootpart4.domain.order.OrderStatus.OPENED;

@Slf4j
@SpringBootTest
public class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void member_insert() {
        Member member = new Member();
        member.setName("sohyeon");
        member.setNickName("soso");
        member.setAge(23);
        member.setAddress("서울특별시 동작구");
        member.setDescription("백엔드 개발자 지망생이에요.");

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(member);

        transaction.commit();
    }

//    @Test
//    void 잘못된_설계() {
//        Member member = new Member();
//        member.setName("sohyeon");
//        member.setAddress("서울시 동작구(만) 움직이면 쏜다.");
//        member.setAge(23);
//        member.setNickName("soso._.hyeon");
//        member.setDescription("백앤드 개발자에요.");
//
//        EntityManager entityManager = emf.createEntityManager();
//        EntityTransaction transaction = entityManager.getTransaction();
//        transaction.begin();
//
//        entityManager.persist(member); // 저장
//        Member memberEntity = entityManager.find(Member.class, 1L); // 영속화된 회원
//
//        Order order = new Order();
//        order.setUuid(UUID.randomUUID().toString());
//        order.setOrderDatetime(LocalDateTime.now());
//        order.setOrderStatus(OPENED);
//        order.setMemo("부재시 전화주세요.");
//        order.setMemberId(memberEntity.getId()); // 외래키를 직접 지정
//
//        entityManager.persist(order);
//        transaction.commit();
//
//        Order orderEntity = entityManager.find(Order.class, order.getUuid());
//        // FK 를 이용해 회원 다시 조회
//        Member orderMemberEntity = entityManager.find(Member.class, orderEntity.getMemberId());
//        // orderEntity.getMember() // 객체중심 설계라면 객체그래프 탐색을 해야하지 않을까?
//        log.info("nick : {}", orderMemberEntity.getNickName());
//    }

    @Test
    void 연관관계_테스트() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        // 주문 Entity
        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("부재 시 문 앞에 놓아주세요");

        entityManager.persist(order);

        // 회원 Entity
        Member member = new Member();
        member.setName("kimsohyeon");
        member.setNickName("soso._.hyeon");
        member.setAddress("서울특별시 동작구");
        member.setAge(23);
        member.setDescription("안녕하세요");

        order.setMember(member);
        member.getOrders().add(order); // 연관관계 주인 아닌 곳에만 SETTING

        entityManager.persist(member);

        transaction.commit();

        entityManager.clear();
        Order entity = entityManager.find(Order.class, order.getUuid());

        log.info("{}", entity.getMember().getNickName());    // 객체 그래프 탐색
        log.info("{}", entity.getMember().getOrders().size());    // 객체 그래프 탐색
        log.info("{}", order.getMember().getOrders().size());    // 객체 그래프 탐색
    }

    @Test
    void 양방향관계_저장_편의메소드() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 주문 Entity
        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("부재 시 문 앞에 놓아주세요");

        entityManager.persist(order);

        // 회원 Entity
        Member member = new Member();
        member.setName("kimsohyeon");
        member.setNickName("soso._.hyeon");
        member.setAddress("서울특별시 동작구");
        member.setAge(23);
        member.setDescription("안녕하세요");

        member.addOrder(order); // 연관관계 편의 메소드 사용

        entityManager.persist(member);

        transaction.commit();
    }

    @Test
    void 객체그래프탐색을_이용한_조회() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 주문 Entity
        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("부재 시 문 앞에 놓아주세요");

        entityManager.persist(order);

        // 회원 Entity
        Member member = new Member();
        member.setName("kimsohyeon");
        member.setNickName("soso._.hyeon");
        member.setAddress("서울특별시 동작구");
        member.setAge(23);
        member.setDescription("안녕하세요");

        member.addOrder(order); // 연관관계 편의 메소드

        entityManager.persist(member);

        transaction.commit();

        entityManager.clear();

        // 회원 조회 (회원의 주문까지 조회)
        Member findMember = entityManager.find(Member.class, 1L);
        log.info("order-memo: {}", findMember.getOrders().get(0).getMemo());

        // 주문 조회 (주문한 회원 조회)
        Order findOrder = entityManager.find(Order.class, findMember.getOrders().get(0).getUuid());
        log.info("member-nickName: {}", findOrder.getMember().getNickName());
    }
}
