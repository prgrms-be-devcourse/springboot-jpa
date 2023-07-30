package com.programmers.jpa;

import com.programmers.week.WeekApplication;
import com.programmers.week.customer.domain.Customer;
import com.programmers.week.item.domain.Car;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Slf4j
@ContextConfiguration(classes = WeekApplication.class)
public class LifeCycleTest {

  @Autowired
  EntityManagerFactory emf;

  @ParameterizedTest
  @CsvSource(value = {"은지|박", "명한|유", "범준|고"}, delimiter = '|' )
  void create_Customer_Success(String firstName, String lastName) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Customer customer = new Customer(firstName, lastName);
    em.persist(customer);
    transaction.commit();

    log.info("고객의 firstName {}, lastName {}", customer.getFirstName(), customer.getLastName());
    assertEquals(firstName, customer.getFirstName());
    assertEquals(lastName, customer.getLastName());
  }

  @ParameterizedTest
  @CsvSource(value = {"은지|박", "명한|유", "범준|고"}, delimiter = '|' )
  void find_Customer_Using_1st_Cache_Success(String firstName, String lastName) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Customer customer = new Customer(firstName, lastName);
    em.persist(customer);
    transaction.commit();
    Customer findCustomer = em.find(Customer.class, customer.getId());

    log.info("1차 캐시를 이용한 고객 조회 ---> ID: {}, firstName: {}, lastName: {}", findCustomer.getId(), findCustomer.getFirstName(), findCustomer.getLastName());
    assertEquals(firstName, findCustomer.getFirstName());
    assertEquals(lastName, findCustomer.getLastName());
    assertEquals(customer.getId(), findCustomer.getId());
  }

  @ParameterizedTest
  @CsvSource(value = {"은지|박", "명한|유", "범준|고"}, delimiter = '|' )
  void find_Customer_Directly_Success(String firstName, String lastName) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Customer customer = new Customer(firstName, lastName);
    em.persist(customer);
    transaction.commit();
    em.clear();
    Customer findCustomer = em.find(Customer.class, customer.getId());

    log.info("DB에서 고객 조회 ---> ID: {}, firstName: {}, lastName: {}", findCustomer.getId(), findCustomer.getFirstName(), findCustomer.getLastName());
    assertEquals(firstName, findCustomer.getFirstName());
    assertEquals(lastName, findCustomer.getLastName());
    assertEquals(customer.getId(), findCustomer.getId());
  }

  @ParameterizedTest
  @CsvSource(value = {"은지|박|영경|나", "명한|유|상민|박", "범준|고|건희|원"}, delimiter = '|' )
  void update_Customer_Success(String firstName, String lastName, String newFirstName, String newLastName) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Customer customer = new Customer(firstName, lastName);
    em.persist(customer);

    log.info("수정 전 고객 ---> ID: {}, firstName: {}, lastName: {}", customer.getId(), customer.getFirstName(), customer.getLastName());
    customer.changeFirstName(newFirstName);
    customer.changeLastName(newLastName);
    transaction.commit();

    Customer findCustomer = em.find(Customer.class, customer.getId());
    log.info("수정 후 고객 ---> ID: {}, firstName: {}, lastName: {}", findCustomer.getId(), findCustomer.getFirstName(), findCustomer.getLastName());
    assertEquals(findCustomer.getId(), customer.getId());
    assertEquals(newFirstName, findCustomer.getFirstName());
    assertEquals(newLastName, findCustomer.getLastName());
  }

  @ParameterizedTest
  @CsvSource(value = {"은지|박", "명한|유", "범준|고"}, delimiter = '|' )
  void delete_Customer_Success(String firstName, String lastName) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    Customer customer = new Customer(firstName, lastName);
    em.persist(customer);

    em.remove(customer);
    transaction.commit();

    assertNull((em.find(Customer.class, customer.getId())));
  }

}
