package com.jpa.springboot.domain.order;

import com.jpa.springboot.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.jpa.springboot.domain.order.OrderStatus.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderItemTest {
    @Autowired
    EntityManagerFactory emf;

    @Test
    void test_Order_OrderItem() {
        //Given
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Order order = new Order();
        order.setId(128L);
        order.setOrderStatus(OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        em.persist(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(10L);
        orderItem.setOrder(order);
        em.persist(orderItem);
        tx.commit();
        em.clear();

        //When
        OrderItem findOrderItem = em.find(OrderItem.class, orderItem.getId());

        //Then - id비교
        assertThat(findOrderItem.getOrder().getId()).isEqualTo(128L);
    }

    @Test
    void test_OrderItem_Item() {
        //Given
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        OrderItem orderItem = new OrderItem();
        orderItem.setId(256L);
        em.persist(orderItem);

        Item item = new Item();
        item.setId(100L);
        item.setPrice(500);
        item.setStockQuantity(200);

        item.setOrderItem(orderItem);
        em.persist(item);

        tx.commit();
        em.clear();

        //When
        Item findItem = em.find(Item.class, item.getId());
        //Then - id 비교
        assertThat(findItem.getOrderItem().getId()).isEqualTo(256L);
    }
}