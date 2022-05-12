package com.kdt.lecture.domain;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderMappingTest {

  @Autowired
  private EntityManagerFactory emf;

  private EntityManager entityManager;

  @BeforeEach
  void setUp() {
    entityManager = emf.createEntityManager();
  }

  @Test
  @DisplayName("주문을 생성하면 회원에 주문이 매핑된다.")
  void test_order_mapping_to_member() {
    //given
    Member member = createMember();

    //when
    Order newOrder = new Order(member);
    Order memberOrder = member.getOrders().get(0);

    //then
    assertThat(memberOrder).usingRecursiveComparison().isEqualTo(newOrder);
  }

  @Test
  @DisplayName("주문상품을 생성하면 주문에 주문상품이 매핑된다.")
  void test_order_item_mapping_to_order() {
    //given
    Member member = createMember();
    Order order = new Order(member);
    Item item = createItem();

    //when
    OrderItem newOrderItem = new OrderItem(order, item, 2);

    //then
    OrderItem orderItem = order.getOrderItems().get(0);
    assertThat(orderItem).usingRecursiveComparison().isEqualTo(newOrderItem);
  }

  @Test
  @DisplayName("주문을 영속화할 때 주문 상품도 함께 영속화된다.")
  void test_order_persist_with_order_item() {
    //given
    Member member = createMember();
    Order order = new Order(member);
    Item item = createItem();
    OrderItem newOrderItem = new OrderItem(order, item, 2);

    //when
    entityManager.persist(order);

    //then
    OrderItem orderItem = entityManager.find(OrderItem.class, newOrderItem.getId());
    assertThat(orderItem).usingRecursiveComparison().isEqualTo(newOrderItem);
  }

  @Test
  @DisplayName("주문을 삭제할 때 주문 상품도 함께 삭제된다.")
  void test_order_delete_with_order_item() {
    //given
    Member member = createMember();
    Order order = new Order(member);
    Item item = createItem();
    OrderItem newOrderItem = new OrderItem(order, item, 2);
    entityManager.persist(order);

    //when
    entityManager.remove(order);

    //then
    OrderItem orderItem = entityManager.find(OrderItem.class, newOrderItem.getId());
    assertThat(orderItem).isNull();
  }

  @Test
  @DisplayName("주문에서 제외된 고아 주문상품은 삭제된다.")
  void test_delete_orphan_order_item() {
    //given
    Member member = createMember();
    Order order = new Order(member);
    Item item = createItem();
    OrderItem newOrderItem = new OrderItem(order, item, 2);
    entityManager.persist(order);
    EntityTransaction transaction = entityManager.getTransaction();

    //when
    transaction.begin();
    order.removeOrderItem(newOrderItem);
    transaction.commit();

    //then
    OrderItem orderItem = entityManager.find(OrderItem.class, newOrderItem.getId());
    assertThat(orderItem).isNull();
  }


  private Member createMember() {
    Member member = new Member("name", "nickname", 20, "서울");
    entityManager.persist(member);
    return member;
  }

  private Item createItem() {
    Item item = new Item(1000, 10);
    entityManager.persist(item);
    return item;
  }
}