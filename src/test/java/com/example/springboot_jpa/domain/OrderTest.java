package com.example.springboot_jpa.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderTest {

  @Autowired
  EntityManagerFactory emf;

  EntityManager em;

  @BeforeEach
  void setup() {
    em = emf.createEntityManager();
  }

  @AfterEach
  void tearDown() {
    em.getTransaction().begin();
    em.createQuery("DELETE FROM Item ").executeUpdate();
    em.createQuery("DELETE FROM OrderItem ").executeUpdate();
    em.createQuery("DELETE FROM Order ").executeUpdate();

    em.getTransaction().commit();
    em.close();
  }

  @Test
  void order_with_cascade_also_automatically_persists_order_item() {

    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    // Given
    Order order = getOrderFixture();
    em.persist(order);

    // When
    OrderItem orderItem1 = getOrderItemFixture();
    orderItem1.setOrder(order);
    transaction.commit();
    // Then
    // Order의 OneToMany에 CascadeType.PERSIST 옵션이 켜져 있을 때만 통과된다.
    assertThat(em.contains(orderItem1)).isTrue();
  }

  private OrderItem getOrderItemFixture() {
    var orderItem1 = new OrderItem();
    orderItem1.setPrice(1500);
    orderItem1.setQuantity(20);
    return orderItem1;
  }

  private Order getOrderFixture() {
    var order = new Order();
    order.setUuid(UUID.randomUUID().toString());
    order.setOrderDatetime(LocalDateTime.now());
    order.setOrderStatus(OrderStatus.OPENED);
    order.setMemo("주문1");
    return order;
  }

  @Test
  @DisplayName("Cascade가 설정되어 있을 때 order의 리스트에서 orderItem을 제거한 것 만으로 orderItem이 컨텍스트와 데이터베이스에서 제거되는가 ")
  void cascade_remove_test1() {

    EntityTransaction transaction = em.getTransaction();
    
    // Given
    transaction.begin();
    Order order = getOrderFixture();
    em.persist(order);
    OrderItem orderItem1 = getOrderItemFixture();
    orderItem1.setOrder(order);
    transaction.commit();

    // When
    transaction.begin();
    order.getOrderItems().remove(orderItem1);
    transaction.commit();

    // Then
    assertThat(em.contains(orderItem1)).isFalse();
  }

  @Test
  @DisplayName("cascadeType.ALL일 때, Order를 컨텍스트에서 remove한 것 만으로 OrderItem이 제거되는가")
  void cascade_remove_test2() {

    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    // Given
    Order order = getOrderFixture();
    em.persist(order);
    OrderItem orderItem1 = getOrderItemFixture();
    orderItem1.setOrder(order);
    transaction.commit();

    // When
    transaction.begin();
    em.remove(order);
    // cascadeType.ALL 일 경우 orderItem도 자동으로 제거
    transaction.commit();

    // Then
    assertThat(em.contains(order)).isFalse();
    assertThat(em.contains(orderItem1)).isFalse();
  }

  @Test
  @DisplayName("orphanremoval이 true일 때 order에서 orderItem을 제거할 경우 orderItem이 db에서 제거되는가")
  void orphan_removal_test() {

    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    // Given
    Order order = getOrderFixture();
    em.persist(order);
    OrderItem orderItem1 = getOrderItemFixture();
    orderItem1.setOrder(order);
    transaction.commit();

    // When
    transaction.begin();
    order.getOrderItems().remove(0);
    transaction.commit();

    // orphanRemoval이 false일 경우 orderItem이 컨텍스트에서 사라지지 않는다.
    // Then
    assertThat(em.contains(orderItem1)).isFalse();
  }

  @Test
  @DisplayName("cascade가 없는 item이 참조하고 있는 orderitem이 컨텍스트에서 제거 될 떄 예외를 던진다.")
  void multi_realational_test() {
    EntityTransaction transaction = em.getTransaction();

    // Given
    transaction.begin();
    var order = getOrderFixture();
    var orderItem = getOrderItemFixture();
    var item = getItemFixture();
    orderItem.setOrder(order);
    item.setOrderItem(orderItem);
    em.persist(order);
    // item은 CASCADE를 사용하지 않으므로 직접 영속화시켜야 한다.
    em.persist(item);
    transaction.commit();

    // Then
    assertThat(em.contains(item)).isTrue();
    assertThatCode(() -> {
      // OrderItem의 items 필드가 CascadeType.ALL일 경우 item까지 제대로 제거된다.
      transaction.begin();
      em.remove(order);
      transaction.commit();
    }).isInstanceOf(PersistenceException.class);

    // Context에서는 제거되어 있다.
    assertThat(em.contains(order)).isFalse();
    assertThat(em.contains(orderItem)).isFalse();
    assertThat(em.contains(item)).isFalse();
  }

  private Item getItemFixture() {
    var item = new Item();
    item.setPrice(1000);
    item.setStockQuantity(10000);
    return item;
  }

}
