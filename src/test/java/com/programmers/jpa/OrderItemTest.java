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
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
@ContextConfiguration(classes = WeekApplication.class)
public class OrderItemTest {

  @Autowired
  EntityManagerFactory emf;

  @ParameterizedTest
  @CsvSource(value = {"SUCCESS|빠른 배송 부탁드려요|1000|5|100000", "CANCELLED|조심히 오세요|2500|2|300000"}, delimiter = '|' )
  void create_OrderItem_Success(OrderStatus orderStatus, String memo, int price, int quantity, long power) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    Order order = new Order(orderStatus, memo);
    Item item = Car.of(price, quantity, power);
    em.persist(order);
    em.persist(item);
    em.flush();

    OrderItem orderItem = new OrderItem(order, item);
    em.persist(orderItem);

    transaction.commit();

    Order findOrder = em.find(Order.class, orderItem.getId());
    log.info("주문 ID: {}, 주문 상품의 주문 ID: {}", findOrder.getId(), orderItem.getOrder().getId());
    log.info("상품 ID: {}, 주문 상품의 상품 ID: {}", item.getId(), orderItem.getItem().getId());
    assertEquals(findOrder.getId(), orderItem.getOrder().getId());
    assertEquals(item.getId(), orderItem.getItem().getId());
  }

}
