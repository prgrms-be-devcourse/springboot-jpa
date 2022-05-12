package com.kdt.lecture.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.kdt.lecture.domain.order.OrderStatus.OPENED;

@Slf4j
@SpringBootTest
class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void insert_Member_Test() {
        Member member = new Member();
        member.setName("kimkihyun");
        member.setAddress("서울시 동작구");
        member.setAge(26);
        member.setNickName("unnokid");
        member.setDescription("공부중입니다");

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(member);
        transaction.commit();
    }

    @Test
    void 잘못된_설계() {
        Member member = new Member();
        member.setName("kanghonggu");
        member.setAddress("서울시 동작구(만) 움직이면 쏜다.");
        member.setAge(33);
        member.setNickName("guppy.kang");
        member.setDescription("백앤드 개발자에요.");

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(member);
        Member memberEntity = entityManager.find(Member.class, 1L);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderStatus(OPENED);
        order.setMemo("부재시 전화주세요.");
        order.setMemberId(memberEntity.getId()); // 외래키를 직접 지정

        entityManager.persist(order);
        transaction.commit();

        Order orderEntity = entityManager.find(Order.class, order.getUuid());
        // FK 를 이용해 회원 다시 조회
        Member orderMemberEntity = entityManager.find(Member.class, orderEntity.getMemberId());
        // orderEntity.getMember() // 객체중심 설계라면 객체그래프 탐색을 해야하지 않을까?
        log.info("nick : {}", orderMemberEntity.getNickName());
    }

    @Test
    void association_Test() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Member member = new Member();
        member.setName("lee sin");
        member.setNickName("god");
        member.setAddress("서울시 동작구");
        member.setAge(33);
        //member에 아직 order를 안넣음
        em.persist(member);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OPENED);
        order.setOrderDateTime(LocalDateTime.now());
        order.setMemo("메모~");
        order.setMember(member);
        //member.setOrders(Lists.newArrayList(order));
        em.persist(order);
        transaction.commit();

        em.clear();;
        Order entity = em.find(Order.class,order.getUuid());

        log.info("{}",order.getMember().getNickName());
        log.info("{}", entity.getMember().getOrders().size());
        log.info("{}", order.getMember().getOrders().size());

    }
}