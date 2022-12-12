package com.example.springbootjpa.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EntityMappingTest {

    @PersistenceUnit
    EntityManagerFactory emf;

    // Order <-> OrderItem : 일대다
    // Item <-> OrderItem : 일대다
    @Test
    @DisplayName("OrderItem 저장시 Item, Order도 함께 저장되는지 하확인")
    void saveOrderItemsThenSaveOrderAndItem() {
        // given
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Order order = new Order(1L, LocalDateTime.now(), OrderStatus.READY);
        Item item = new Item(1L, 1000, 5);
        OrderItem orderItem = new OrderItem(1L, order, item);

        // when
        transaction.begin();
        entityManager.persist(order);
        entityManager.persist(item);
        entityManager.persist(orderItem);
        transaction.commit();

        // then
        OrderItem findOrderItem = entityManager.find(OrderItem.class, orderItem.getId());
        Item findItem = entityManager.find(Item.class, item.getId());
        Order findOrder = entityManager.find(Order.class, order.getId());

        assertThat(findOrderItem).usingRecursiveComparison().isEqualTo(orderItem);
        assertThat(findItem).usingRecursiveComparison().isEqualTo(item);
        assertThat(findOrder).usingRecursiveComparison().isEqualTo(order);
    }

    @Test
    @DisplayName("연관관계 편의 메서드 확인")
    void mappingMethodSuccess() {
        // given
        Order order = new Order(1L, LocalDateTime.now(), OrderStatus.READY);
        Item item = new Item(1L, 1000, 5);
        OrderItem orderItem = new OrderItem(1L);

        // when
        orderItem.setItem(item);
        orderItem.setOrder(order);

        // then
        assertThat(order.getOrderItems()).contains(orderItem);
        assertThat(item.getOrderItems()).contains(orderItem);
    }

    
}
