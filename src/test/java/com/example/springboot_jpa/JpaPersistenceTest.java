package com.example.springboot_jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springboot_jpa.entity.Customer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaPersistenceTest {

  @Autowired
  private EntityManagerFactory emf;

  private EntityManager em;

  @BeforeEach
  void setup() {
    em = emf.createEntityManager();
  }

  @AfterEach
  void tearDown() {
    var transaction = em.getTransaction();
    transaction.begin();
    em.createQuery("DELETE FROM Customer ").executeUpdate();
    transaction.commit(); // Query 또한 transaction으로 commit되어야 쿼리가 실행된다.
    em.close();
  }

  @Test
  void insert_test() {

    // Given
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    var customer = new Customer(1L, "Minsung", "Kim"); // 비영속적

    // when
    em.persist(customer); // 영속적
    transaction.commit(); // flush(), 쿼리 실행

    // then
    var persistedCustomer = em.find(Customer.class, 1L);
    assertThat(persistedCustomer).isEqualTo(customer);

  }

  @Test
  void update() {
    // Given
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    var customer = new Customer(1L, "Minsung", "Kim"); // 비영속적
    em.persist(customer); // 영속화
    transaction.commit();

    // when
    transaction.begin();
    customer.setLastName("changed");
    customer.setFirstName("yaho");
    transaction.commit(); // snapshot과 비교 후 db에 쿼리 실행

    // Then
    var persistedCustomer = em.find(Customer.class, 1L);
    assertThat(persistedCustomer).isEqualTo(customer);
  }

  @Test
  void delete() {
    // Given
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    var customer = new Customer(1L, "Minsung", "Kim"); // 비영속적
    em.persist(customer); // 영속화
    transaction.commit();

    // when
    transaction.begin();
    em.remove(customer);
    transaction.commit(); // snapshot과 비교 후 db에 쿼리 실행

    // Then
    var persistedCustomer = em.find(Customer.class, 1L);
    assertThat(persistedCustomer).isNull();
  }

  @Test
  void update_with_rollback_should_not_update_persistence_context() {
    // Given
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    var customer = new Customer(1L, "Minsung", "Kim"); // 비영속적
    em.persist(customer); // 영속화
    transaction.commit();

    // when
    transaction.begin();
    customer.setLastName("changed");
    customer.setFirstName("yaho");
    transaction.rollback();

    // Then
    var rollbackCustomer = em.find(Customer.class, 1L);
    assertThat(rollbackCustomer).isNotSameAs(customer); // 다른 인스턴스 (customer의 필드값이 바뀌는 것이 아니므로)
    assertThat(rollbackCustomer.getFirstName()).isEqualTo("Minsung");
    assertThat(rollbackCustomer.getLastName()).isEqualTo("Kim");
  }

  @Test
  void detach_test() {

    // Given
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    var customer = new Customer(1L, "Minsung", "Kim"); // 비영속적
    em.persist(customer); // 영속화
    transaction.commit();

    // When
    transaction.begin();
    em.detach(customer);
    customer.setFirstName("떨어짐");
    customer.setLastName("out"); // 비영속적이기 때문에 변경 사항 반영 X
    transaction.commit();

    // Then
    var persistedCustomer = em.find(Customer.class, 1L);
    assertThat(persistedCustomer).isNotEqualTo(customer);
    assertThat(persistedCustomer.getLastName()).isEqualTo("Kim");
    assertThat(persistedCustomer.getFirstName()).isEqualTo("Minsung");
  }

  @Test
  void merge_test() {

    // Given
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    var customer = new Customer(1L, "Minsung", "Kim"); // 비영속적
    em.persist(customer); // 영속화
    transaction.commit();

    // When
    em.detach(customer);
    customer.setFirstName("떨어짐");
    customer.setLastName("out");
    em.merge(customer);// transaction을 설정하지 않아도 변경 사항을 db에 반영한다.

    // Then
    var persistedCustomer = em.find(Customer.class, 1L);
    assertThat(persistedCustomer).isEqualTo(customer);
  }

}
