package com.kdt.jpa.order;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderOrderItemMappingTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;
    private EntityTransaction transaction;

    private final String orderId = UUID.randomUUID().toString();
    private long orderItemId;

    private final Item item = new Item(10000, 20);
    private final OrderItem orderItem = new OrderItem(10000, 2);
    private final Order order = new Order(orderId, LocalDateTime.now(), OrderStatus.OPENED, "문앞에 놔주세요.");

    @BeforeEach
    void setData() {
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(item);

        orderItem.setItem(item);
        entityManager.persist(orderItem);

        order.addOrderItem(orderItem);
        entityManager.persist(order);

        transaction.commit();
        orderItemId = orderItem.getId();
    }

    @AfterEach
    void clean() {
        transaction.begin();
        entityManager.createQuery("DELETE FROM OrderItem ").executeUpdate();
        entityManager.createQuery("DELETE FROM Order ").executeUpdate();
        entityManager.createQuery("DELETE FROM Item ").executeUpdate();
        transaction.commit();
    }

    @Test
    @DisplayName("주문상품을 통해 상품을 조회한다.")
    void findItemInOrderItem() {
        // given
        entityManager.clear();

        // when
        OrderItem result = entityManager.find(OrderItem.class, orderItemId);

        // then
        assertThat(result.getItem().getStockQuantity())
                .isEqualTo(20);
    }

    @Test
    @DisplayName("주문을 통해 주문상품을 조회한다.")
    void findOrderItemInOrder() {
        // given
        entityManager.clear();

        // when
        Order result = entityManager.find(Order.class, orderId);

        // then
        assertThat(result.getOrderItems().get(0).getPrice())
                .isEqualTo(10000);
    }

    @Test
    @DisplayName("주문을 삭제하면 주문상품도 삭제된다.")
    void deleteOrderItemInOrder() {
        // given

        // when
        transaction.begin();
        entityManager.remove(order);
        transaction.commit();

        // then
        assertThat(entityManager.contains(orderItem))
                .isFalse();
    }
}
