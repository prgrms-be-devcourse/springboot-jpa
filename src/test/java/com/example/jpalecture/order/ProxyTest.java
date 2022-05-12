package com.example.jpalecture.order;

import com.example.jpalecture.domain.order.Member;
import com.example.jpalecture.domain.order.Order;
import com.example.jpalecture.domain.order.OrderItem;
import com.example.jpalecture.domain.order.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@SpringBootTest
public class ProxyTest {

    @Autowired
    EntityManagerFactory emf;

    private String uuid = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Order order = new Order();
        order.setUuid(uuid);
        order.setMemo("부재시 전화주세요.");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OPENED);
        entityManager.persist(order);

        Member member = new Member();
        member.setName("kanghonggu");
        member.setNickName("guppy.kang");
        member.setAge(33);
        member.setAddress("서울시 동작구");
        member.setDescription("화이팅~");
        member.addOrder(order);
        entityManager.persist(member);

        transaction.commit();
    }

    @Test
    void proxy() {
        EntityManager entityManager = emf.createEntityManager();
        Order order = entityManager.find(Order.class, uuid);

        Member member = order.getMember();
        log.info("MEMBER USE BEFORE IS-LOADED: {}", emf.getPersistenceUnitUtil().isLoaded(member));
        member.getNickName();
        log.info("MEMBER USE AFTER IS-LOADED: {}", emf.getPersistenceUnitUtil().isLoaded(member));
    }

    @Test
    void move_persist() {
        EntityManager entityManager = emf.createEntityManager();
        Order order = entityManager.find(Order.class, uuid);

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(10);
        orderItem.setPrice(1000);
        order.addOrderItem(orderItem);
        transaction.commit();

        entityManager.clear();

        Order order2 = entityManager.find(Order.class, uuid);
        transaction.begin();
        order2.getOrderItems().remove(0);
        transaction.commit();
    }

}
