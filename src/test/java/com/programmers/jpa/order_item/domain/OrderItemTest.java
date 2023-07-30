package com.programmers.jpa.order_item.domain;

import com.programmers.jpa.item.domain.Car;
import com.programmers.jpa.item.domain.Item;
import com.programmers.jpa.order.domain.Order;
import com.programmers.jpa.order.domain.OrderStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderItemTest {

    @Autowired
    EntityManagerFactory emf;

    @DisplayName("주문 상품을 생성할 수 있다.")
    @Test
    void createOrderItem() {
        //given
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Order order = new Order(OrderStatus.SUCCESS, "메모");
        Item item = Car.of(1000, 10, 1000);
        em.persist(item);
        em.persist(order);
        transaction.commit();

        //when
        transaction.begin();
        OrderItem orderItem = new OrderItem(order, item);
        em.persist(orderItem);
        transaction.commit();

        //then
        OrderItem foundOrderItem = em.find(OrderItem.class, orderItem.getId());
        Assertions.assertThat(foundOrderItem).isEqualTo(orderItem);
    }
}
