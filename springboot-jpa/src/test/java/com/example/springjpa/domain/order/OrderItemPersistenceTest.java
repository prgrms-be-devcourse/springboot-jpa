package com.example.springjpa.domain.order;

import com.example.springjpa.domain.common.EntityManagerTest;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static com.example.springjpa.domain.order.OrderStatus.OPENED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class OrderItemPersistenceTest extends EntityManagerTest {
    @Test
    @Description("연관관계 메소드를 통해 Item을 추가 할 수 있다.")
    void testAddItem() {
        Member member = new Member("태산", UUID.randomUUID().toString(), 10, "주소", "Desc", new ArrayList<>());
        Order order = new Order("메모", OPENED, LocalDateTime.now(), member, new ArrayList<>());
        OrderItem orderItem = new OrderItem(100, 1, order, new ArrayList<>());
        Item item = new Item(100, 10);
        execWithTransaction(() -> {
            entityManager.persist(member);
            entityManager.persist(order);
            entityManager.persist(orderItem);
            entityManager.persist(item);

            orderItem.addItem(item);
        });

        OrderItem findOrderItem = entityManager.find(OrderItem.class, orderItem.getId());
        assertThat(findOrderItem.getItems().size()).isEqualTo(1);
    }

    @Test
    @Description("OrderItem에 Item이 추가될 item의 OrderItem이 udpate되어야 한다.")
    void testAddItemUpdate() {
        Member member = new Member("태산", UUID.randomUUID().toString(), 10, "주소", "Desc", new ArrayList<>());
        Order order = new Order("메모", OPENED, LocalDateTime.now(), member, new ArrayList<>());
        OrderItem orderItem = new OrderItem(100, 1, order, new ArrayList<>());
        Item item = new Item(100, 10);
        execWithTransaction(() -> {
            entityManager.persist(member);
            entityManager.persist(order);
            entityManager.persist(orderItem);
            entityManager.persist(item);

            orderItem.addItem(item);
        });

        Item findItem = entityManager.find(Item.class, item.getId());
        assertThat(findItem.getOrderItem().getId()).isEqualTo(orderItem.getId());
    }

    @Test
    @Description("객체 그래프를 탐색할 수 있다.")
    void testGraphSearch() {
        Member member = new Member("태산", UUID.randomUUID().toString(), 10, "주소", "Desc", new ArrayList<>());
        Order order = new Order("메모", OPENED, LocalDateTime.now(), member, new ArrayList<>());
        OrderItem orderItem = new OrderItem(100, 1, order, new ArrayList<>());
        Item item = new Item(100, 10);
        execWithTransaction(() -> {
            entityManager.persist(member);
            entityManager.persist(order);
            entityManager.persist(orderItem);
            entityManager.persist(item);
            orderItem.addItem(item);
        });

        OrderItem findOrderItem = entityManager.find(OrderItem.class, orderItem.getId());
        assertAll(
                () -> assertThat(findOrderItem.getItems()).contains(item),
                () -> assertThat(findOrderItem.getOrder().getUuid()).isEqualTo(order.getUuid())
        );
    }

}