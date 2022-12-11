package com.example.springjpa.domain.order;

import com.example.springjpa.domain.common.EntityManagerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.springjpa.domain.order.EntityUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class OrderPersistenceTest extends EntityManagerTest {
    Member member;
    Order order;
    OrderItem orderItem;
    OrderItem orderItem2;

    @BeforeEach
    void init() {
        member = getNewMember();
        order = getNewOrder(member);
        orderItem = getNewOrderItem(100, 1, order);
        orderItem2 = getNewOrderItem(100, 1, order);
    }

    @Test
    @DisplayName("연관관계 메소드를 통해 주문 목록을 추가 할 수 있다.")
    void testAddOrderItem() {
        execWithTransaction(() -> {
            entityManager.persist(member);
            entityManager.persist(orderItem);
            entityManager.persist(orderItem2);
            entityManager.persist(order);

            order.addOrderItem(orderItem);
            order.addOrderItem(orderItem2);
        });
        Order findOrder = entityManager.find(Order.class, order.getUuid());
        assertThat(findOrder.getOrderItems()).hasSize(2);
    }

    @Test
    @DisplayName("주문에 아이템을 추가했을 때, OrderItem이 가진 Order가 Update되어야 한다.")
    void testAddOrderItemUpdate() {
        execWithTransaction(() -> {
            entityManager.persist(member);
            entityManager.persist(orderItem);
            entityManager.persist(orderItem2);
            entityManager.persist(order);

            order.addOrderItem(orderItem);
            order.addOrderItem(orderItem2);
        });

        OrderItem findOrderItem = entityManager.find(OrderItem.class, orderItem.getId());
        OrderItem findOrderItem2 = entityManager.find(OrderItem.class, orderItem2.getId());
        assertAll(
                () -> assertThat(findOrderItem.getOrder().getUuid()).isEqualTo(order.getUuid()),
                () -> assertThat(findOrderItem2.getOrder().getUuid()).isEqualTo(order.getUuid())
        );
    }

    @Test
    @DisplayName("객체 그래프를 탐색할 수 있다.")
    void testGraphSearch() {
        execWithTransaction(() -> {
            entityManager.persist(member);
            entityManager.persist(orderItem);
            entityManager.persist(orderItem2);
            entityManager.persist(order);

            order.addOrderItem(orderItem);
            order.addOrderItem(orderItem2);
        });

        Order findOrder = entityManager.find(Order.class, order.getUuid());
        assertAll(
                () -> assertThat(findOrder.getMember().getId()).isEqualTo(member.getId()),
                () -> assertThat(findOrder.getOrderItems()).contains(orderItem),
                () -> assertThat(findOrder.getOrderItems()).contains(orderItem2)
        );
    }
}