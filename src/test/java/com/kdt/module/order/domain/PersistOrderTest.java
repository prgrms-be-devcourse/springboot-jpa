package com.kdt.module.order.domain;

import com.kdt.module.customer.domain.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static com.kdt.module.order.domain.OrderStatus.ORDERED;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersistOrderTest {
    @Autowired
    private EntityManagerFactory factory;

    private final Customer customer = new Customer("hejow", "moon");
    private final Order order = Order.builder()
            .status(ORDERED)
            .orderTime(LocalDateTime.now())
            .memo("")
            .build();

    @Test
    @DisplayName("addOrder을 사용하면 order가 저장되어야 한다.")
    void saveOrderTest() {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.persist(customer);
        customer.addOrder(order);

        transaction.commit();
        assertThat(entityManager.contains(customer)).isTrue();
        assertThat(entityManager.contains(order)).isTrue();
    }

    @Test
    @DisplayName("저장한 주문을 삭제할 수 있어야 한다.")
    void removeOrderTest() {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.persist(customer);
        customer.addOrder(order);

        transaction.commit();
        transaction.begin();

        customer.removeOrder(order);
        transaction.commit();

        assertThat(entityManager.contains(order)).isFalse();
    }

    @Test
    @DisplayName("clear를 한 뒤 삭제할 수 있어야 한다.")
    void removeOrder_Success_AfterClear() {
        // given
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(customer);
        customer.addOrder(order);

        transaction.commit();
        entityManager.clear();
        assertThat(entityManager.contains(order)).isFalse();

        Customer mergedCustomer = entityManager.merge(customer);
        Order mergedOrder = mergedCustomer.getOrders().get(0);

        transaction.begin();
        assertThat(entityManager.contains(mergedOrder)).isTrue();

        mergedCustomer.removeOrder(mergedOrder);
        transaction.commit();

        assertThat(entityManager.contains(mergedOrder)).isFalse();
    }
}
