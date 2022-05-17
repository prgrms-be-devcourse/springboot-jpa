package com.prgrms.springbootjpa.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@DataJpaTest
public class OrderPersistenceTest {
    @Autowired
    EntityManagerFactory emf;

    @Test
    @DisplayName("member, order 연관관계 테스트")
    void memberOrderRelationTest() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member member = new Member("jerry", "jerry", 25, "address");
        em.persist(member);

        Order order = new Order(UUID.randomUUID().toString(), OrderStatus.OPENED, "조심", LocalDateTime.now());
        order.setMember(member);
        em.persist(order);

        em.flush();
        em.clear();

        Member foundMember = em.find(Member.class, member.getId());
        Order foundOrder = em.find(Order.class, order.getUuid());

        assertThat(foundOrder.getMember(), samePropertyValuesAs(foundMember));

        tx.commit();
    }

    @Test
    @DisplayName("order, orderItem 연관관계 테스트")
    void OrderOrderItemRelationTest() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Order order = new Order(UUID.randomUUID().toString(), OrderStatus.OPENED, "조심", LocalDateTime.now());
        em.persist(order);

        OrderItem orderItem = new OrderItem(1000, OrderStatus.OPENED);
        orderItem.setOrder(order);
        em.persist(orderItem);

        em.flush();
        em.clear();

        Order foundOrder = em.find(Order.class, order.getUuid());
        OrderItem foundOrderItem = em.find(OrderItem.class, orderItem.getId());

        assertThat(foundOrderItem.getOrder(), samePropertyValuesAs(foundOrder));

        tx.commit();
    }

    @Test
    @DisplayName("item, orderItem 연관관계 테스트")
    void ItemOrderItemRelationTest() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Item item = new Item("A", 1000, 10 );
        em.persist(item);

        OrderItem orderItem = new OrderItem(1000, OrderStatus.OPENED);
        orderItem.setItem(item);
        em.persist(orderItem);

        em.flush();
        em.clear();

        Item foundItem = em.find(Item.class, item.getId());
        OrderItem foundOrderItem = em.find(OrderItem.class, orderItem.getId());

        assertThat(foundOrderItem.getItem(), samePropertyValuesAs(foundItem));

        tx.commit();
    }

}
