package com.kdt.lecture.domain.order;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class OrderToOrderItemTest {

    @Autowired
    EntityManagerFactory emf;

    EntityManager entityManager;
    EntityTransaction entityTransaction;
    Order order;
    OrderItem orderItem;

    @BeforeEach
    void entityPersist(){
        entityManager = emf.createEntityManager();
        entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        order = EntityRegister.order(entityManager);
        orderItem = EntityRegister.orderItem(entityManager);

        order.addOrderItems(orderItem);
        entityTransaction.commit();
    }

    @Test
    @DisplayName("조회")
    void findTest() {
        assertThat(order.getOrderItems().get(0)).usingRecursiveComparison().isEqualTo(orderItem);
        assertThat(orderItem.getOrder()).usingRecursiveComparison().isEqualTo(order);
    }

    @Test
    @DisplayName("업데이트")
    void updateTest() {
        entityTransaction.begin();
        OrderItem orderItemEntity = entityManager.find(OrderItem.class, this.orderItem.getId());
        orderItemEntity.setQuantity(10);
        entityTransaction.commit();

        entityManager.clear();

        Order orderEntity = entityManager.find(Order.class, order.getId());
        assertThat(orderEntity.getOrderItems().get(0).getQuantity()).isEqualTo(10);
    }

    @Test
    @DisplayName("삭제")
    void deleteTest() {
        entityTransaction.begin();
        entityManager.remove(order);
        entityManager.remove(orderItem);
        entityTransaction.commit();

        Order orderEntity = entityManager.find(Order.class, this.order.getId());
        OrderItem orderItemEntity = entityManager.find(OrderItem.class, orderItem.getId());

        assertThat(orderEntity).isNull();
        assertThat(orderItemEntity).isNull();
    }
}
