package com.kdt.lecture.domain.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderItemToItemTest {

    @Autowired
    EntityManagerFactory emf;

    EntityManager entityManager;
    EntityTransaction entityTransaction;
    Item item;
    OrderItem orderItem;

    @BeforeEach
    void entityPersist(){
        entityManager = emf.createEntityManager();
        entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        item = EntityRegister.item(entityManager);
        orderItem = EntityRegister.orderItem(entityManager);
        orderItem.addItem(item);
        entityTransaction.commit();
    }

    @Test
    @DisplayName("조회")
    void findTest() {
        assertThat(item.getOrderItem()).usingRecursiveComparison().isEqualTo(orderItem);
        assertThat(orderItem.getItems().get(0)).usingRecursiveComparison().isEqualTo(item);
    }

    @Test
    @DisplayName("업데이트")
    void updateTest() {
        entityTransaction.begin();
        Item itemEntity = entityManager.find(Item.class, item.getId());
        itemEntity.setPrice(2500);
        entityTransaction.commit();

        entityManager.clear();

        OrderItem orderItemEntity = entityManager.find(OrderItem.class, orderItem.getId());
        assertThat(orderItemEntity.getItems().get(0).getPrice()).isEqualTo(2500);
    }

    @Test
    @DisplayName("삭제")
    void deleteTest() {
        entityTransaction.begin();
        entityManager.remove(item);
        entityManager.remove(orderItem);
        entityTransaction.commit();

        Item itemEntity = entityManager.find(Item.class, item.getId());
        OrderItem orderItemEntity = entityManager.find(OrderItem.class, orderItem.getId());

        assertThat(itemEntity).isNull();
        assertThat(orderItemEntity).isNull();
    }
}
