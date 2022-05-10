package com.prgrms.springbootjpa.domain;

import com.prgrms.springbootjpa.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
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

  @BeforeEach
  void setUp() {
    repository.deleteAll();
  }

  @Test
  @DisplayName("Customer 데이터 저장")
  void testCreateCustomer() {
    EntityManager entityManager = emf.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    transaction.begin();

    Customer customer = new Customer();
    customer.setId(1L);
    customer.setFirstName("first");
    customer.setLastName("last");

    entityManager.persist(customer);
    transaction.commit();
  }
}
