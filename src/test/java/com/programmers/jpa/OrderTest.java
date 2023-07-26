package com.programmers.jpa;
import com.programmers.week.WeekApplication;
import com.programmers.week.order.domain.Order;
import com.programmers.week.order.domain.OrderStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = WeekApplication.class)
public class OrderTest {

  @Autowired
  EntityManagerFactory emf;

  @Test
  void createOrder(){
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Order order = new Order(OrderStatus.SUCCESS, "메모메모");

    em.persist(order);
    transaction.commit();

    Order findOrder = em.find(Order.class, 1L);
    System.out.println(String.format("주문 ===> %s", findOrder));
  }
}
