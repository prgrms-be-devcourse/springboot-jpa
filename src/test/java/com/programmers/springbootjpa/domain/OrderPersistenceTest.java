package com.programmers.springbootjpa.domain;

import com.programmers.springbootjpa.domain.order.Member;
import com.programmers.springbootjpa.domain.order.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.programmers.springbootjpa.domain.order.OrderStatus.OPENED;

@Slf4j
@SpringBootTest
public class OrderPersistenceTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void member_insert() {
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

        entityManager.persist(member);  // 저장
        Member memberEntity = entityManager.find(Member.class, 1L);     // 영속화된 회원

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
        // orderEntity.getMember() // 객체중심 설계라면 이렇게 해야하지 않을까?
        log.info("nick : {}", orderMemberEntity.getNickName());
    }

    @Test
    void 연관관계_테스트() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Member member = new Member();
        member.setName("hyeonseo");
        member.setNickName("JUNG");
        member.setAddress("서울시 마포구");
        member.setAge(24);

        entityManager.persist(member);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OPENED);
        order.setOrderDateTime(LocalDateTime.now());
        order.setMemo("메모입니다.");
        order.setMember(member);

        entityManager.persist(order);

        transaction.commit();

        entityManager.clear();
        Order entity = entityManager.find(Order.class, order.getUuid());

        log.info("{}", entity.getMember().getNickName());
        log.info("{}", entity.getMember().getOrders().size());
        log.info("{}", order.getMember().getOrders().size());
    }
}
