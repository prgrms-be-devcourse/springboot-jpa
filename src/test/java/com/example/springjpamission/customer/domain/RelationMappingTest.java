package com.example.springjpamission.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springjpamission.order.domain.Car;
import com.example.springjpamission.order.domain.Order;
import com.example.springjpamission.order.domain.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
public class RelationMappingTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    @DisplayName("Order와 Cosuntemr가 매핑이 잘 되어있는지 확인한다.")
    void order_customer_mappingTest(){
        //given
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        //when
        transaction.begin();

        String uuid = UUID.randomUUID().toString();
        Order order = Order.builder()
                        .id(uuid).memo("---").build();

        Name name = new Name("영운", "윤");
        Customer customer = Customer.builder().
                name(name).build();

        order.setCustomer(customer);
        em.persist(order);
        transaction.commit();
        em.clear();
        Order findOrder = em.find(Order.class, order.getId());

        //then
        assertThat(findOrder.getCustomer().getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("Order와 OrderItem 매핑이 잘 되어있는지 확인한다.")
    void order_orderItem_mappingTest() {
        //given
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        //when
        transaction.begin();
        String uuid = UUID.randomUUID().toString();
        Order order = Order.builder()
                .id(uuid).memo("---").build();

        OrderItem orderItem = OrderItem.builder().price(1000).quantity(10).build();
        order.addOrderItem(orderItem);

        em.persist(order);
        transaction.commit();
        em.clear();
        Order updatedOrder = em.find(Order.class, uuid);

        //then
        assertThat(updatedOrder.getOrderItems().get(0).getPrice()).isEqualTo(1000);
        assertThat(updatedOrder.getOrderItems().get(0).getQuantity()).isEqualTo(10);
    }

    @Test
    @DisplayName("orderItemr과 Item매핑이 잘 되어있는지 확인한다.")
    void orderItem_item_mappingTest(){
        //given
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        //when
        transaction.begin();

        OrderItem orderItem = OrderItem.builder().price(1000).quantity(10).build();

        Car item = new Car(2000,100,100);

        orderItem.setItem(item);

        em.persist(orderItem);
        transaction.commit();

        OrderItem updatedOrderItem = em.find(OrderItem.class, orderItem.getId());
        Car item1 = (Car)updatedOrderItem.getItem();

        // then
        assertThat(item1.getPower()).isEqualTo(200);
    }

}
