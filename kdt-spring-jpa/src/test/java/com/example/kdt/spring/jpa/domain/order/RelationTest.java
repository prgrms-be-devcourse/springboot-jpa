package com.example.kdt.spring.jpa.domain.order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;


import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
// beforeall static으로 안 쓰기 위함.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RelationTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static EntityTransaction entityTransaction;

    @BeforeAll
    void setup() {
        entityManager = entityManagerFactory.createEntityManager();;
        entityTransaction = entityManager.getTransaction();
    }

    @AfterEach
    void clearup() {
        entityManager.clear();
    }

    @DisplayName("주문에는 고객이 없으면 안된다.")
    @Test
    void order_must_have_member_test() {
        // given
        entityTransaction.begin();
        Order order = Order.builder()
            .orderStatus(OrderStatus.OPENED)
            .orderDatetime(LocalDateTime.now())
            .build();

        // when
        Item item = new Food(10000, 100,"백종원");
        OrderItem orderItem = new OrderItem(item, 33);
        orderItem.attachOrder(order);

        entityManager.persist(order);

        // then
        assertThrows(ConstraintViolationException.class,
            () -> entityTransaction.commit());
    }

    @Test
    @DisplayName("주문에는 아이템이 없으면 안된다.")
    void order_must_have_item() {
        // given
        entityTransaction.begin();
        Member member = Member.builder()
            .age(23)
            .name("test")
            .nickName("testNickName")
            .address("testAddress")
            .build();
        Order order = Order.builder()
            .orderStatus(OrderStatus.OPENED)
            .orderDatetime(LocalDateTime.now())
            .build();

        // when
        order.attachMember(member);
        entityManager.persist(order);

        // then
        assertThrows(ConstraintViolationException.class,
            () -> entityTransaction.commit());
    }

    @DisplayName("주문아이템을 생성할 수 있다.")
    @Test
    void insert_order_item_test() {
        // given
        Item item = new Food(10000, 100, "백종원");
        OrderItem orderItem = new OrderItem(item, 12);

        // when
        entityManager.persist(orderItem);

        // then
        OrderItem retrievedOrderItem = entityManager.find(OrderItem.class, orderItem.getId());
        assertThat(orderItem).usingRecursiveComparison().isEqualTo(retrievedOrderItem);
        log.info("{}",retrievedOrderItem.getItemPrice());
    }

    @DisplayName("주문아이템을 생성하면 재고가 감소된다.")
    @Test
    void item_stockQuantity_decrement_test() {
        // given
        Item item = new Food(10000, 100, "백종원");
        OrderItem orderItem = new OrderItem(item, 12);

        // when
        entityManager.persist(orderItem);

        // then
        assertEquals(88, item.getStockQuantity());
    }

    @Test
    @DisplayName("주문을 생성할 수 있다.")
    void insert_order_test() {
        // Given
        entityTransaction.begin();
        Order order = Order.builder()
            .memo("testMemo")
            .orderStatus(OrderStatus.OPENED)
            .orderDatetime(LocalDateTime.now())
            .build();
        Member member = Member.builder()
            .age(23)
            .name("test")
            .nickName("testNickName")
            .address("testAddress")
            .build();
        Item item = new Food(10000, 100,"백종원");
        OrderItem orderItem = new OrderItem(item, 33);
        orderItem.attachOrder(order);
        order.attachMember(member);

        // When
        entityManager.persist(order);
        entityTransaction.commit();

        // Then
        Order retrievedOrder = entityManager.find(Order.class, order.getId());
        assertThat(order).usingRecursiveComparison().isEqualTo(retrievedOrder);
    }
}
