package com.example.springjpa.domain.order;

import com.example.springjpa.domain.common.EntityManagerTest;
import com.example.springjpa.exception.NoStockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.springjpa.domain.order.EntityUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderItemPersistenceTest extends EntityManagerTest {

    private final int itemQuantity = 5;
    Member member;
    Order order;
    OrderItem orderItem;
    Item item;

    @BeforeEach
    void init() {
        member = getNewMember();
        order = getNewOrder(member);
        orderItem = getNewOrderItem(100, 1, order);
        item = Item.builder().price(1000).stockQuantity(itemQuantity).build();
    }

    @Test
    @DisplayName("연관관계 메서드로 Order를 저장 할 수 있다.")
    void testSetOrder() {
        execWithTransaction(() -> {
            entityManager.persist(member);
            entityManager.persist(order);
            entityManager.persist(orderItem);

            orderItem.setOrder(order);
        });

        OrderItem findOrderItem = entityManager.find(OrderItem.class, orderItem.getId());
        Order findOrder = entityManager.find(Order.class, order.getUuid());

        assertAll(
                () -> assertThat(findOrderItem.getOrder().getUuid()).isEqualTo(order.getUuid()),
                () -> assertThat(findOrder.getOrderItems()).hasSize(1)
        );
    }

    @Test
    @DisplayName("연관관계 메서드로 Item를 저장 할 수 있다.")
    void testSetItem() {
        execWithTransaction(() -> {
            entityManager.persist(member);
            entityManager.persist(order);
            entityManager.persist(item);
            entityManager.persist(orderItem);

            orderItem.setItem(item);
        });

        OrderItem findOrderItem = entityManager.find(OrderItem.class, orderItem.getId());
        Item findItem = entityManager.find(Item.class, item.getId());

        assertAll(
                () -> assertThat(findOrderItem.getId()).isEqualTo(orderItem.getId()),
                () -> assertThat(findItem.getOrderItems()).hasSize(1),
                () -> assertThat(findItem.getStockQuantity()).isEqualTo(itemQuantity - 1)
        );
    }

    @Test
    @DisplayName("수량이 1개 미만인 아이템은 저장할 수 없다.")
    void testSetItemException() {
        Item noStockItem = Item.builder().price(1000).stockQuantity(0).build();

        assertThrows(NoStockException.class,
                () -> execWithTransaction(() -> {
                    entityManager.persist(member);
                    entityManager.persist(order);
                    entityManager.persist(noStockItem);
                    entityManager.persist(orderItem);

                    orderItem.setItem(noStockItem);
                })
        );
    }

//    @Test
//    @DisplayName("연관관계 메소드를 통해 Item을 추가 할 수 있다.")
//    void testAddItem() {
//
//        Member member = getNewMember();
//        Order order = getNewOrder(member);
//        OrderItem orderItem = getNewOrderItem(100, 1, order);
//        Item item = Item.builder().price(1000).stockQuantity(5).build();
//        execWithTransaction(() -> {
//            entityManager.persist(member);
//            entityManager.persist(item);
//            entityManager.persist(order);
//            entityManager.persist(orderItem);
//
//            orderItem.setItem(item);
//        });
//
//        OrderItem findOrderItem = entityManager.find(OrderItem.class, orderItem.getId());
//        assertThat(findOrderItem.getItem().getOrderItems()).hasSize(1);
//    }

//    @Test
//    @DisplayName("OrderItem에 Item이 추가될 item의 OrderItem이 udpate되어야 한다.")
//    void testAddItemUpdate() {
//        Member member = getNewMember();
//        Order order = new Order("메모", OPENED, LocalDateTime.now(), member, new ArrayList<>());
//        OrderItem orderItem = new OrderItem(100, 1, order, new ArrayList<>());
//        Item item = new Item(100, 10);
//        execWithTransaction(() -> {
//            entityManager.persist(member);
//            entityManager.persist(order);
//            entityManager.persist(orderItem);
//            entityManager.persist(item);
//
//            orderItem.addItem(item);
//        });
//
//        Item findItem = entityManager.find(Item.class, item.getId());
//        assertThat(findItem.getOrderItem().getId()).isEqualTo(orderItem.getId());
//    }
//
//    @Test
//    @DisplayName("객체 그래프를 탐색할 수 있다.")
//    void testGraphSearch() {
//        Member member = getNewMember();
//        Order order = new Order("메모", OPENED, LocalDateTime.now(), member, new ArrayList<>());
//        OrderItem orderItem = new OrderItem(100, 1, order, new ArrayList<>());
//        Item item = new Item(100, 10);
//        execWithTransaction(() -> {
//            entityManager.persist(member);
//            entityManager.persist(order);
//            entityManager.persist(orderItem);
//            entityManager.persist(item);
//            orderItem.addItem(item);
//        });
//
//        OrderItem findOrderItem = entityManager.find(OrderItem.class, orderItem.getId());
//        assertAll(
//                () -> assertThat(findOrderItem.getItems()).contains(item),
//                () -> assertThat(findOrderItem.getOrder().getUuid()).isEqualTo(order.getUuid())
//        );
//    }

}