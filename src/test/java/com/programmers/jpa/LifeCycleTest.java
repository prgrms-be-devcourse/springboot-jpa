package com.programmers.jpa;

import com.programmers.week.WeekApplication;
import com.programmers.week.customer.domain.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ContextConfiguration(classes = WeekApplication.class)
public class LifeCycleTest {

  @Autowired
  EntityManagerFactory emf;

  @Test
  void save() {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Customer customer = new Customer("은지", "박");
    em.persist(customer);
    transaction.commit();
    System.out.println(String.format("저장 customer %s", customer));
  }

  @Test
  void findUsing1stCache() {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Customer customer = new Customer("은지", "박");
    em.persist(customer);
    transaction.commit();

    Customer customer1 = em.find(Customer.class, 1L);
    System.out.println(String.format("1차 캐시 이용해서 조회 customer %s", customer1));
  }

  @Test
  void findDirectly() {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Customer customer = new Customer("은지", "박");
    em.persist(customer);
    transaction.commit();
    em.clear();
    Customer customer1 = em.find(Customer.class, 1L);
    System.out.println(String.format("DB 조회 customer %s", customer1));
  }

  @Test
  void update() {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Customer customer = new Customer("은지", "박");
    em.persist(customer);

    System.out.println(String.format("변경 전 customer %s %s", customer.getFirstName(), customer.getLastName()));

    customer.changeFirstName("명한");
    customer.changeLastName("유");
    transaction.commit();

    System.out.println(String.format("변경 전 customer %s %s", customer.getFirstName(), customer.getLastName()));
  }

  @Test
  void delete() {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Customer customer = new Customer("은지", "박");
    em.persist(customer);

    Customer customer1 = em.find(Customer.class, 1L);
    em.remove(customer1);

    transaction.commit();
    System.out.println(String.format("삭제 customer %s", customer1));
  }

}
