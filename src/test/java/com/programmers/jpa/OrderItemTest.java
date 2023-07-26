package com.programmers.jpa;

import com.programmers.week.WeekApplication;
import com.programmers.week.item.domain.Car;
import com.programmers.week.item.domain.Item;
import com.programmers.week.order.domain.Order;
import com.programmers.week.order.domain.OrderStatus;
import com.programmers.week.orderItem.domain.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = WeekApplication.class)
public class OrderItemTest {

  @Autowired
  EntityManagerFactory emf;

  @Test
  void createOrderItem() {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    Order order = new Order(OrderStatus.SUCCESS, "메에모오");
    Item item = Car.of(10000, 5, 100);
    em.persist(order);
    em.persist(item);
    em.flush();

    OrderItem orderItem = new OrderItem(order, item);
    transaction.commit();
    em.persist(orderItem);

    Order findOrder = em.find(Order.class, 1L);
    System.out.println(String.format("주문 ===> %s", findOrder));
  }

}
