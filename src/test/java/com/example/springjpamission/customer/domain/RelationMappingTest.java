package com.example.springjpamission.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springjpamission.order.domain.Car;
import com.example.springjpamission.order.domain.Item;
import com.example.springjpamission.order.domain.Order;
import com.example.springjpamission.order.domain.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.UUID;
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
    void order_customer_mappingTest(){
        //given
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        //when
        transaction.begin();

        String uuid = UUID.randomUUID().toString();
        Order order = new Order();
        order.setMemo("---");
        order.setId(uuid);

        Customer customer = new Customer();
        customer.setFirstName("영운");
        customer.setLastName("윤");

        order.setCustomer(customer);
        em.persist(order);
        transaction.commit();
        em.clear();
        Order findOrder = em.find(Order.class, order.getId());

        //then
        assertThat(findOrder.getCustomer().getFirstName()).isEqualTo("영운");
        assertThat(findOrder.getCustomer().getLastName()).isEqualTo("윤");
    }

    @Test
    void order_orderItem_mappingTest() {
        //given
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        //when
        transaction.begin();
        String uuid = UUID.randomUUID().toString();
        Order order = new Order();
        order.setMemo("---");
        order.setId(uuid);

        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(1000);
        orderItem.setQuantity(10);
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
    void orderItem_item_mappingTest(){
        //given
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        //when
        transaction.begin();

        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(1000);
        orderItem.setQuantity(10);

        Car item = new Car();
        item.setPrice(1000);
        item.setPower(200);
        item.setStockQuantity(100);

        orderItem.addItem(item);

        em.persist(orderItem);
        transaction.commit();

        OrderItem updatedOrderItem = em.find(OrderItem.class, orderItem.getId());
        Car item1 = (Car)updatedOrderItem.getItems().get(0);

        // then
        assertThat(item1.getPower()).isEqualTo(200);
    }
}
