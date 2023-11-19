package com.devcourse.springbootjpaweekly.domain;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional(propagation = Propagation.NEVER)
public class OrderItemMappingTest {

    static final String DELETE_FROM_ITEM = "DELETE FROM item";
    static final String DELETE_FROM_ORDER_ITEM = "DELETE FROM order_item";

    @Autowired
    EntityManagerFactory emf;
    EntityManager entityManager;
    EntityTransaction transaction;

    @BeforeEach
    void setup() {
        entityManager = emf.createEntityManager();
    }

    @AfterEach
    void cleanup() {
        transaction.begin();

        entityManager.createNativeQuery(DELETE_FROM_ORDER_ITEM)
                .executeUpdate();
        entityManager.createNativeQuery(DELETE_FROM_ITEM)
                .executeUpdate();

        transaction.commit();

        entityManager.close();
    }

    @DisplayName("주문 상품 엔티티에서 매핑된 대상 상품을 성실하고 즉각적으로 가져온다.")
    @Test
    void testItemMappingTest() {
        //given
        transaction = entityManager.getTransaction();

        transaction.begin();

        Item item = Item.builder()
                .name("정신 번적 들고 싶을 땐 쌉쌀한 아메리카노 한 모금 쓴 커피")
                .price(1300)
                .stockQuantity(100)
                .build();
        OrderItem orderItem = OrderItem.builder()
                .item(item)
                .quantity(10)
                .build();

        entityManager.persist(item);
        entityManager.persist(orderItem);

        transaction.commit();

        //when
        entityManager.clear();

        OrderItem foundOrderItem = entityManager.find(OrderItem.class, orderItem.getId());
        Item actualItem = foundOrderItem.getItem();

        //then
        boolean isPracticallyFetched = emf.getPersistenceUnitUtil()
                .isLoaded(actualItem);

        assertThat(isPracticallyFetched).isTrue();
        assertThat(actualItem)
                .hasFieldOrPropertyWithValue("id", item.getId())
                .hasFieldOrPropertyWithValue("price", item.getPrice());
    }

    @DisplayName("")
    @Test
    void testOrderMappingTest() {
        //given
        transaction = entityManager.getTransaction();

        transaction.begin();

        Order order = Order.builder()
                .orderStatus(OrderStatus.OPENED)
                .memo("전자 기기")
                .build();
        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .build();

        entityManager.persist(order);
        entityManager.persist(orderItem);

        transaction.commit();

        //when
        entityManager.clear();

        OrderItem foundOrderItem = entityManager.find(OrderItem.class, orderItem.getId());
        Order actualOrder = foundOrderItem.getOrder();

        //then
        boolean isPracticallyFetched = emf.getPersistenceUnitUtil()
                .isLoaded(actualOrder);

        assertThat(isPracticallyFetched).isFalse();
        assertThat(actualOrder).isNotNull()
                .hasFieldOrPropertyWithValue("id", order.getId())
                .hasFieldOrPropertyWithValue("memo", order.getMemo());
    }
}
