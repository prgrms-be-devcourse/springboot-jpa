package com.prgrms.springbootjpa.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class EntityMappingTest {

  @Autowired
  EntityManagerFactory emf;

  @Test
  void Order_OrderItem_연관관계() {
    EntityManager entityManager = emf.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();

    Order order = new Order();
    order.setId(1L);
    entityManager.persist(order);

    OrderItem orderItem = new OrderItem();
    orderItem.setId(10L);
    orderItem.setOrder(order);
    entityManager.persist(orderItem);

    transaction.commit();

    entityManager.clear();
    OrderItem foundOrderItem = entityManager.find(OrderItem.class, orderItem.getId());

    assertThat(foundOrderItem.getOrder().getId()).isEqualTo(1L);
  }

  @Test
  void OrderItem_Item_연관관계() {
    EntityManager entityManager = emf.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();

    OrderItem orderItem = new OrderItem();
    orderItem.setId(10L);
    entityManager.persist(orderItem);

    Item item = new Item();
    item.setId(100L);
    item.setOrderItem(orderItem);
    entityManager.persist(item);

    transaction.commit();

    entityManager.clear();
    Item foundItem = entityManager.find(Item.class, item.getId());

    assertThat(foundItem.getOrderItem().getId()).isEqualTo(10L);
  }
}
