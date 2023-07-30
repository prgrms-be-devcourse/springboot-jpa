package com.programmers.jpa;

import com.programmers.week.WeekApplication;
import com.programmers.week.order.domain.Order;
import com.programmers.week.order.domain.OrderStatus;
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
public class OrderTest {

  @Autowired
  EntityManagerFactory emf;

  @ParameterizedTest
  @CsvSource(value = {"SUCCESS|안전운전 하세요", "SUCCESS|abcdefghijklmnopqrstuvwxyz"}, delimiter = '|' )
  void create_Order_Success(OrderStatus orderStatus, String memo) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Order order = new Order(orderStatus, memo);
    em.persist(order);
    transaction.commit();

    Order findOrder = em.find(Order.class, order.getId());
    log.info("주문 ID: {}, 주문 상태: {}, 주문 메모: {}", order.getId(), order.getOrderStatus(), order.getMemo());
    assertEquals(findOrder.getId(), order.getId());
  }

}
