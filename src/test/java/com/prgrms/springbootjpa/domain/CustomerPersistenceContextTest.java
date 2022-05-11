package com.prgrms.springbootjpa.domain;

import com.prgrms.springbootjpa.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Slf4j
@SpringBootTest
public class CustomerPersistenceContextTest {

  @Autowired
  CustomerRepository repository;

  @Autowired
  EntityManagerFactory emf;

  private static Customer customer;

  @BeforeAll
  static void beforeAll() {
    customer = new Customer();
    customer.setId(1L);
    customer.setFirstName("first");
    customer.setLastName("last");
  }

  @BeforeEach
  void setUp() {
    repository.deleteAll();
  }

  @Test
  @DisplayName("customers 테이블에 데이터 저장")
  void testInsertCustomer() {
    EntityManager entityManager = emf.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    entityManager.persist(customer);
    transaction.commit();
  }

  @Test
  @DisplayName("customers 테이블 조회")
  void testFindCustomer() {
    EntityManager entityManager = emf.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    entityManager.persist(customer);
    transaction.commit();
    entityManager.detach(customer);

    Customer selected = entityManager.find(Customer.class, 1L);
    log.info("First Name: {}, Last Name: {}", selected.getFirstName(), selected.getLastName());
  }

  @Test
  @DisplayName("1차 캐시의 Customer 조회")
  void testFindCustomerInCache() {
    EntityManager entityManager = emf.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    entityManager.persist(customer);
    transaction.commit();

    Customer selected = entityManager.find(Customer.class, 1L);
    log.info("First Name: {}, Last Name: {}", selected.getFirstName(), selected.getLastName());
  }

  @Test
  @DisplayName("Customer 수정")
  void testUpdateCustomer() {
    EntityManager entityManager = emf.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    entityManager.persist(customer);
    transaction.commit();

    transaction.begin();
    customer.setFirstName("name");
    customer.setLastName("seong");
    transaction.commit();
  }

  @Test
  @DisplayName("Customer 삭제")
  void testDeleteCustomer() {
    EntityManager entityManager = emf.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();
    entityManager.persist(customer);
    transaction.commit();

    transaction.begin();
    entityManager.remove(customer);
    transaction.commit();
  }
}