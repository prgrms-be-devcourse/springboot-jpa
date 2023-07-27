package com.example.prog.orderingsystem.order.domain;

import com.example.prog.orderingsystem.order.factory.ItemFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("연관 관계 매핑 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest
public class OderMappingTest {

    @Autowired
    EntityManagerFactory managerFactory;

    Long customerId = 1L;

    Item item1;
    Item item2;
    Item item3;

    @BeforeEach
    void init() {
        EntityManager em = managerFactory.createEntityManager();
        EntityTransaction et = em.getTransaction();

        item1 = ItemFactory.getNewItem();
        item2 = ItemFactory.getNewItem();
        item3 = ItemFactory.getNewItem();

        et.begin();
        em.persist(item1);
        em.persist(item2);
        em.persist(item3);
        et.commit();
    }

    @DisplayName("주문 아이템 생성 테스트")
    @Test
    void order_item_create() {
        // given
        EntityManager em = managerFactory.createEntityManager();
        EntityTransaction et = em.getTransaction();

        Order order1 = Order.builder()
                .customerId(customerId)
                .memo("string")
                .build();

        Order order2 = Order.builder()
                .customerId(customerId)
                .memo("next")
                .build();

        order1.addItem(item1);
        order2.addItem(item2);
        order2.addItem(item3);

        // When
        et.begin();
        em.persist(order1);
        em.persist(order2);

        OrderItem orderItem1 = em.find(OrderItem.class, item1.getId());
        OrderItem orderItem2 = em.find(OrderItem.class, item2.getId());
        OrderItem orderItem3 = em.find(OrderItem.class, item3.getId());

        Order savedOrder1 = em.find(Order.class, order1.getId());
        Order savedOrder2 = em.find(Order.class, order2.getId());
        et.commit();

        // Then
        assertAll(
                () -> assertThat(order1.getOrderItems()).containsExactly(orderItem1),
                () -> assertThat(order2.getOrderItems()).containsExactly(orderItem2, orderItem3)
        );

        assertAll(
                () -> assertThat(savedOrder1.getOrderItems()).hasSize(1),
                () -> assertThat(savedOrder2.getOrderItems()).hasSize(2)
        );
    }

}
