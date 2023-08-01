package com.example.jpaweekly.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.jpaweekly.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PersistenceContextTest {

  @Autowired
  CustomerRepository repository;
  @Autowired
  EntityManagerFactory emf;
  EntityManager entityManager;
  EntityTransaction transaction;

  @BeforeEach
  void setUp() {
    entityManager = emf.createEntityManager();
    transaction = entityManager.getTransaction();
  }

  @Test
  @DisplayName("엔티티 영속화")
  void persist() {

    // given
    transaction.begin();

    Customer customer = Customer.builder()
        .firstName("준일")
        .lastName("배")
        .build();

    // when
    entityManager.persist(customer);

    // then
    transaction.commit();
    assertThat(entityManager.contains(customer)).isTrue();
  }

  @Test
  @DisplayName("엔티티 조회 - DB 조회")
  void find_Using_DB() {

    // given
    transaction.begin();

    Customer customer = Customer.builder()
        .firstName("준일")
        .lastName("배")
        .build();

    entityManager.persist(customer);
    transaction.commit();

    // when
    entityManager.detach(customer);

    Customer foundCustomer = entityManager.find(Customer.class, customer.getId());

    // then
    assertThat(foundCustomer.getId()).isEqualTo(customer.getId());
  }

  @Test
  @DisplayName("엔티티 조회 - 1차 캐시 조회")
  void find_Using_DirtyCheck() {

    // given
    transaction.begin();

    Customer customer = Customer.builder()
        .firstName("준일")
        .lastName("배")
        .build();

    entityManager.persist(customer);

    // when
    transaction.commit();

    Customer foundCustomer = entityManager.find(Customer.class, customer.getId());

    // then
    assertThat(foundCustomer.getId()).isEqualTo(customer.getId());
  }

  @Test
  @DisplayName("엔티티 수정")
  void update_Entity() {

    // given
    transaction.begin();

    Customer customer = Customer.builder()
        .firstName("준일")
        .lastName("배")
        .build();

    entityManager.persist(customer);
    transaction.commit();

    // when
    transaction.begin();
    Customer foundCustomer = entityManager.find(Customer.class, customer.getId());

    customer.updateFirstName("JunIl");
    customer.updateLastName("Bae");

    transaction.commit();

    Customer updatedCustomer = entityManager.find(Customer.class, foundCustomer.getId());

    // then
    assertThat(updatedCustomer.getId()).isEqualTo(foundCustomer.getId());
    assertThat(updatedCustomer.getFirstName()).isEqualTo(foundCustomer.getFirstName());
    assertThat(updatedCustomer.getLastName()).isEqualTo(foundCustomer.getLastName());
  }

  @Test
  @DisplayName("엔티티 삭제")
  void delete_Customer() {

    // given
    transaction.begin();

    Customer customer = Customer.builder()
        .firstName("준일")
        .lastName("배")
        .build();

    entityManager.persist(customer);
    transaction.commit();

    // when
    transaction.begin();

    Customer foundCustomer = entityManager.find(Customer.class, customer.getId());

    entityManager.remove(foundCustomer);

    transaction.commit();

    // then
    assertThat(entityManager.contains(foundCustomer)).isFalse();
  }
}
