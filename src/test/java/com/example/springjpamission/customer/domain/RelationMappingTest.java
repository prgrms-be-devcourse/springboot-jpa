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
        Order order = new Order (uuid, "---");

        Name name = new Name("영운", "윤");
        Customer customer =  new Customer(name);

        order.setCustomer(customer);
        em.persist(order);
        transaction.commit();
        em.clear();

        //then
        Order findOrder = em.find(Order.class, order.getId());
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
        Order order = new Order(uuid , "---");

        OrderItem orderItem = new OrderItem( 1000 , 10);
        order.addOrderItem(orderItem);

        em.persist(order);
        transaction.commit();
        em.clear();

        //then
        Order updatedOrder = em.find(Order.class, uuid);
        assertThat(updatedOrder.getOrderItems().get(0).getPrice()).isEqualTo(1000);
        assertThat(updatedOrder.getOrderItems().get(0).getQuantity()).isEqualTo(10);
    }

    @Test
    @DisplayName("orderItem과 item 매핑이 잘 되어있는지 확인한다.")
    void orderItem_item_mappingTest() {
        //given
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        //when
        transaction.begin();
        OrderItem orderItem = new OrderItem(100, 1);

        Car car = new Car(100, 5, 1);
        orderItem.setItem(car);

        em.persist(orderItem);
        transaction.commit();

        //then
        OrderItem findOrderItem = em.find(OrderItem.class, orderItem.getId());
        assertThat(findOrderItem.getItem().getPrice()).isEqualTo(100);
        assertThat(findOrderItem.getItem().getStockQuantity()).isEqualTo(5);
    }
}
