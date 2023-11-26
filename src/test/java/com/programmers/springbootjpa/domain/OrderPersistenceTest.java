package com.programmers.springbootjpa.domain;

import com.programmers.springbootjpa.domain.entity.Member;
import com.programmers.springbootjpa.domain.entity.Order;
import com.programmers.springbootjpa.domain.entity.OrderStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@DisplayName("Order 양방향 매핑 테스트")
class OrderPersistenceTest {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    EntityManagerFactory emf;


    private EntityManager entityManager;
    private EntityTransaction transaction;
    private Order order;
    private Member member;

    @BeforeEach
    void init() {
        entityManager = emf.createEntityManager();
        transaction = entityManager.getTransaction();
        order = new Order();
        order.setId(UUID.randomUUID());
        order.setMemo("MEMO");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OPENED);
        member = new Member();
        member.setName("David Lee");
        member.setNickName("NickName");
        member.setAge(33);
        member.setAddress("Seoul, Korea");
        member.setDescription("EMPTY MEMBER");
    }

    @Test
    @DisplayName("양방향 관계 저장")
    void testSave() {
        transaction.begin();
        entityManager.persist(order);
        member.getOrders().add(order);
        entityManager.persist(member);
        transaction.commit();
    }

    @Test
    @DisplayName("양방향 관계 저장 편의 메소드")
    void testSaveWithMethod() {
        transaction.begin();
        entityManager.persist(order);
        member.addOrder(order);
        entityManager.persist(member);
        transaction.rollback();
    }

    @Test
    @DisplayName("객체그래프 탐색을 이용한 조회")
    void testFind() {
        transaction.begin();

        entityManager.persist(order);

        member.addOrder(order);

        entityManager.persist(member);

        transaction.commit();

        entityManager.clear();

        Member findMember = entityManager.find(Member.class, 1L);
        log.info("order-memo: {}", findMember.getOrders().get(0).getMemo());

        Order findOrder = entityManager.find(Order.class, findMember.getOrders().get(0).getId());
        log.info("member-nickName: {}", findOrder.getMember().getNickName());
    }
}
