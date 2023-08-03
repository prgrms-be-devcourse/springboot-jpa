package com.example.springjpamission.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springjpamission.order.domain.Car;
import com.example.springjpamission.order.domain.Order;
import com.example.springjpamission.order.domain.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
class RelationMappingTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    @DisplayName("order, order_item, item간 매핑이 잘 되어있는지 확인한다.")
    void order_orderItem_item_mappingTest() {
        //given
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Customer customer = new Customer(new Name("별", "김"));

        //when
        transaction.begin();

        String id = UUID.randomUUID().toString();
        Order order = new Order(id, "----", 1000, 1, customer);
        Car car = new Car(1000, 1, 500);
        OrderItem orderItem = new OrderItem(1000, 1, order, car);
        order.addOrderItem(orderItem);

        em.persist(order);
        transaction.commit();
        em.clear();

        //then
        Order updatedOrder = em.find(Order.class, id);
        OrderItem updatedOrderItem = em.find(OrderItem.class, orderItem.getId());
        Car item = (Car) updatedOrderItem.getItem();

        assertThat(updatedOrder.getOrderItems().get(0).getPrice()).isEqualTo(1000);
        assertThat(item.getPower()).isEqualTo(500);
    }

}
