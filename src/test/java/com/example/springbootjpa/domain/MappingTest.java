package com.example.springbootjpa.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class MappingTest {

    @Autowired
    EntityManagerFactory emf;

    @Test
    void order_orderItem_item_test() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        // 추가하기
        transaction.begin();
        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OPENED);
        order.setMemo("빠른 배송 부탁합니다");

        Food item = new Food();
        item.setPrice(2000);
        item.setStockQuantity(5);
        item.setChef("suy2on");

        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(2);
        orderItem.setPrice(4000);

        entityManager.persist(order);
        entityManager.persist(item);
        entityManager.persist(orderItem);
        order.addOrderItem(orderItem);
        item.addOrderItem(orderItem);
        transaction.commit();   // insert쿼리 3개

        transaction.begin();

        //////////////////
        Order findOrder = entityManager.find(Order.class, order.getUuid());
        assertThat(findOrder.getOrderItemList().size(), is(1));

        entityManager.clear();

        Item findItem = entityManager.find(Item.class, item.getId());
        assertThat(findItem.getOrderItemList().size(), is(1));


    }


}
