package com.example.springjpa.domain.order;

import com.example.springjpa.domain.common.EntityManagerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static com.example.springjpa.domain.order.OrderStatus.OPENED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class OrderPersistenceTest extends EntityManagerTest {
    @Test
    @DisplayName("연관관계 메소드를 통해 주문 목록을 추가 할 수 있다.")
    void testAddOrderItem() {
        Member member = new Member("태산", UUID.randomUUID().toString(), 10, "주소", "Desc", new ArrayList<>());
        Order order = new Order("메모", OPENED, LocalDateTime.now(), member, new ArrayList<>());
        OrderItem orderItem = new OrderItem(100, 1, order, new ArrayList<>());
        OrderItem orderItem2 = new OrderItem(200, 2, order, new ArrayList<>());
        execWithTransaction(() -> {
            entityManager.persist(member);
            entityManager.persist(orderItem);
            entityManager.persist(orderItem2);
            entityManager.persist(order);

            order.addOrderItem(orderItem);
            order.addOrderItem(orderItem2);
        });

        Order findOrder = entityManager.find(Order.class, order.getUuid());
        assertThat(findOrder.getOrderItems().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("주문에 아이템을 추가했을 때, OrderItem이 가진 Order가 Update되어야 한다.")
    void testAddOrderItemUpdate() {
        Member member = new Member("태산", UUID.randomUUID().toString(), 10, "주소", "Desc", new ArrayList<>());
        Order order = new Order("메모", OPENED, LocalDateTime.now(), member, new ArrayList<>());
        OrderItem orderItem = new OrderItem(100, 1, order, new ArrayList<>());
        OrderItem orderItem2 = new OrderItem(200, 2, order, new ArrayList<>());
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
        Member member = new Member("태산", UUID.randomUUID().toString(), 10, "주소", "Desc", new ArrayList<>());
        Order order = new Order("메모", OPENED, LocalDateTime.now(), member, new ArrayList<>());
        OrderItem orderItem = new OrderItem(100, 1, order, new ArrayList<>());
        OrderItem orderItem2 = new OrderItem(200, 2, order, new ArrayList<>());
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