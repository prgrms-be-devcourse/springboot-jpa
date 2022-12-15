package com.prgrms.springbootjpa.domain.order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.prgrms.springbootjpa.domain.order.OrderStatus.*;

@Slf4j
@SpringBootTest
public class OrderPersistenceTest {
    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    @DisplayName("FK를 이용해 회원을 다시 조회한다.")
    void testUsingFK() {
        Member member = new Member();
        member.setName("Changgyu Kim");
        member.setAddress("서울");
        member.setAge(25);
        member.setNickName("구구");
        member.setDescription("백둥이");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(member);
        Member memberEntity = entityManager.find(Member.class, 1L);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OPENED);
        order.setMemo("부재시 전화주세요.");
        order.setMemberId(memberEntity.getId());            // FK 직접 지정

        entityManager.persist(order);
        transaction.commit();

        Order orderEntity = entityManager.find(Order.class, order.getUuid());
        // FK를 이용해 멤버 테이블에서 다시 조회
        Member orderMemberEntity = entityManager.find(Member.class, orderEntity.getMemberId());
        // 객체지향과 관계형 데이터베이스의 패러다임의 불일치를 해소했는가...?
        log.info("Nickname : {}", orderMemberEntity.getNickName());
    }

    @Test
    @DisplayName("연관관계를 매핑하여 가져올 수 있다.")
    void testMapping() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Member member = new Member();
        member.setName("Changgyu Kim");
        member.setAddress("서울");
        member.setAge(25);
        member.setNickName("구구");
        member.setDescription("백둥이");

        entityManager.persist(member);

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setMemo("부재시 연락주세요.");
        order.setMember(member);

        entityManager.persist(order);

        transaction.commit();

        entityManager.clear();

        Order entity = entityManager.find(Order.class, order.getUuid());

        log.info("{}", entity.getMember().getName());           // 객체 그래프 탐색
        log.info("{}", entity.getMember().getOrders().size());
        log.info("{}", order.getMember().getOrders().size());
    }
}
