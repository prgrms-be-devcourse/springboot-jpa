package com.example.springjpa.repository;

import com.example.springjpa.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
@Slf4j
public class RelationTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void member_test() {
        Member member = new Member();
        member.setName("geuno");
        member.setAddress("관악");
        member.setAge(30);
        member.setNickName("gilgil");

    }

    @Test
    void orderTest() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Member member = new Member();
        member.setName("geuno");
        member.setAddress("관악");
        member.setAge(30);
        member.setNickName("gilgil");
        em.persist(member);

        Order order = new Order();
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.ACCEPTED);
        order.setMemo("주문 접수");
        order.setUuid(UUID.randomUUID().toString());
        order.setMember(member);
        em.persist(order);

        transaction.commit();
        em.clear();

        Order order1 = em.find(Order.class, order.getUuid());

        log.info("{}", order1.getMember().getNickName());
        log.info("{}", order1.getMember().getOrders());
        log.info("{}", order.getMember().getOrders());
    }

    @Test
    void orderItemTest() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        Item item = new Item();
        item.setPrice(1500);
        item.setStockQuantity(100);
        em.persist(item);

        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(item.getPrice());
        orderItem.setQuantity(3);
        orderItem.setItem(item);
        em.persist(orderItem);

        Order order = new Order();
        order.addOrderItem(orderItem);
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.ACCEPTED);
        order.setMemo("주문 접수");
        order.setUuid(UUID.randomUUID().toString());
        em.persist(order);
        transaction.commit();
    }
}
