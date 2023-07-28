package com.kdt.module.order;

import com.kdt.module.customer.domain.Customer;
import com.kdt.module.item.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static com.kdt.module.order.OrderStatus.ORDERED;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersistOrderTest {
    private final Customer customer = new Customer("hejow", "moon");
    private final Order order = Order.builder()
            .status(ORDERED)
            .orderTime(LocalDateTime.now())
            .memo("")
            .build();

    private final OrderItem orderItem = new OrderItem(1500, 2);
    private final Item item = new Item(1500, 2);

    @Autowired
    private EntityManagerFactory factory;
    private EntityManager entityManager;

    @BeforeEach
    void initManager() {
        entityManager = factory.createEntityManager();
    }

    @Nested
    @DisplayName("고객과 주문 영속화 테스트")
    class customerAndOrderPersistenceTest {
        @Test
        @DisplayName("주문 추가하기로 주문이 저장되어야 한다.")
        void saveOrder_Success_ByAddOrder() {
            // given
            EntityTransaction transaction = entityManager.getTransaction();

            transaction.begin();
            entityManager.persist(customer);

            // when
            customer.addOrder(order);
            transaction.commit();

            // then
            assertThat(entityManager.contains(customer)).isTrue();
            assertThat(customer.getId()).isNotNull();
            assertThat(entityManager.contains(order)).isTrue();
            assertThat(order.getId()).isNotNull();
        }

        @Test
        @DisplayName("고객의 주문 제거하기로 주문을 삭제할 수 있어야 한다.")
        void removeOrder_Success_ByRemoveOrderOfCustomer() {
            // given
            EntityTransaction transaction = entityManager.getTransaction();

            transaction.begin();

            entityManager.persist(customer);
            customer.addOrder(order);

            transaction.commit();
            transaction.begin();

            // when
            customer.removeOrder(order);
            transaction.commit();

            // then
            assertThat(entityManager.contains(order)).isFalse();
        }

        @Test
        @DisplayName("clear를 한 뒤에도 주문을 삭제할 수 있어야 한다.")
        void removeOrder_Success_AfterClearPersistenceContext() {
            // given
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(customer);
            customer.addOrder(order);

            transaction.commit();
            entityManager.clear();

            Customer mergedCustomer = entityManager.merge(customer);
            Order mergedOrder = mergedCustomer.getOrders().get(0);

            transaction.begin();

            // when
            mergedCustomer.removeOrder(mergedOrder);
            transaction.commit();

            // then
            assertThat(entityManager.contains(mergedOrder)).isFalse();
        }
    }

    @Nested
    @DisplayName("주문과 주문 상품 영속화 테스트")
    class orderAndOrderItemPersistenceTest {
        @Test
        @DisplayName("주문 상품 추가하기로 주문 상품이 저장되어야 한다.")
        void saveOrderItem_Success_ByAddOrderItem() {
            // given
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(order);

            // when
            order.addOrderItem(orderItem);
            transaction.commit();

            // then
            assertThat(orderItem.getId()).isNotNull();
            assertThat(entityManager.contains(orderItem)).isTrue();
        }

        @Test
        @DisplayName("주문의 주문 상품 제거하기로 주문 상품이 제거되어야 한다.")
        void removeOrderItem_Success_ByRemoveOrderItemOfOrder() {
            // given
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(order);
            order.addOrderItem(orderItem);

            transaction.commit();
            transaction.begin();

            // when
            order.removeOrderItem(orderItem);
            transaction.commit();

            // then
            assertThat(entityManager.contains(orderItem)).isFalse();
        }

        @Test
        @DisplayName("clear를 한 뒤에도 주문 상품을 삭제할 수 있어야 한다.")
        void removeOrderItem_Success_AfterClearPersistenceContext() {
            // given
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(order);
            order.addOrderItem(orderItem);

            transaction.commit();
            entityManager.clear();

            Order findOrder = entityManager.find(Order.class, order.getId());
            OrderItem findOrderItem = findOrder.getOrderItems().get(0);
            transaction.begin();

            // when
            findOrder.removeOrderItem(findOrderItem);
            transaction.commit();

            // then
            assertThat(entityManager.contains(orderItem)).isFalse();
            assertThat(entityManager.contains(findOrderItem)).isFalse();
        }
    }

    @Nested
    @DisplayName("주문 상품과 상품 영속화 테스트")
    class orderItemAndItemPersistenceTest {
        @Test
        @DisplayName("상품을 지정하면 상품이 저장되고 업데이트 쿼리가 발생해야 한다.")
        void saveItem_Success_BySetItem() {
            // given
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(orderItem);

            // when
            orderItem.setItem(item);
            transaction.commit();

            // then
            assertThat(item.getId()).isNotNull();
            assertThat(entityManager.contains(item)).isTrue();
        }

        @Test
        @DisplayName("null을 매핑하면 상품이 제거되어야 한다.")
        void removeItem_Success_BySetNULL() {
            // given
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(orderItem);
            orderItem.setItem(item);

            transaction.commit();
            transaction.begin();

            // when
            orderItem.setItem(null);
            transaction.commit();

            // then
            assertThat(entityManager.contains(item)).isFalse();
        }
    }
}
