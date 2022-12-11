package com.example.springbootpart4.domain.order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@SpringBootTest
public class ProxyTest {

    @Autowired
    EntityManagerFactory emf;

    String uuid;

    @BeforeEach
    void setUp() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 주문 엔티티
        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setMemo("부재시 전화주세요.");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OPENED);

        uuid = order.getUuid();

        entityManager.persist(order);

        // 회원 엔티티
        Member member = new Member();
        member.setName("kanghonggu");
        member.setNickName("guppy.kang");
        member.setAge(33);
        member.setAddress("서울시 동작구만 움직이면쏜다.");
        member.setDescription("KDT 화이팅");

        member.addOrder(order); // 연관관계 편의 메소드 사용
        entityManager.persist(member);
        transaction.commit();
    }

    @Test
    void proxy_test() {
        EntityManager entityManager = emf.createEntityManager();

        Order order = entityManager.find(Order.class, uuid);
        Member member = order.getMember();
        log.info("MEMBER USE BEFORE IS-LOADED: {}", emf.getPersistenceUnitUtil().isLoaded(member)); // member 객체는 proxy
        String nickName = member.getNickName(); // use
        log.info("MEMBER USE AFTER IS-LOADED: {}", emf.getPersistenceUnitUtil().isLoaded(member));  // member 객체는 entity
    }

    @Test
    void move_persist_test() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Order order = entityManager.find(Order.class, uuid);    // 영속 상태

        transaction.begin();

        OrderItem item = new OrderItem();   // 준영속 상태
        item.setPrice(1000);
        item.setQuantity(100);

        order.addOrderItem(item); // 영속성 전이 : 영속 상태로 바뀌었다.

        transaction.commit();
        entityManager.clear();

        // 고아객체 테스트
        Order order2 = entityManager.find(Order.class, uuid);    // 영속 상태

        transaction.begin();

        order2.getOrderItems().remove(0);   // 고아 상태 : flush 순간 RDB 에서도 삭제하겠다

        transaction.commit();   // flush
    }

}
