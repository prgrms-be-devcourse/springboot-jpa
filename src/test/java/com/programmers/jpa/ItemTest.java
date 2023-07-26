package com.programmers.jpa;

import com.programmers.week.WeekApplication;
import com.programmers.week.item.domain.Car;
import com.programmers.week.item.domain.Food;
import com.programmers.week.item.domain.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = WeekApplication.class)
public class ItemTest {

  @Autowired
  EntityManagerFactory emf;

  @Test
  void createCar() {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Item item = Car.of(1000, 10, 1000);
    em.persist(item);
    transaction.commit();

    Item car = em.find(Item.class, 1L);
    System.out.println(String.format("자동차 아이템 ===> %s", car));
  }

  @Test
  void createFood() {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Item item = Food.of(1000, 10, "유명한");
    em.persist(item);
    transaction.commit();

    Item food = em.find(Item.class, 1L);
    System.out.println(String.format("음식 아이템 ===> %s", food));
  }

}
