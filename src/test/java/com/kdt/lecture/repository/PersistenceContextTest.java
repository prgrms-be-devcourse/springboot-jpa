package com.kdt.lecture.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kdt.lecture.domain.Customer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersistenceContextTest {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private EntityManagerFactory emf;

  private EntityManager entityManager;

  private EntityTransaction transaction;

  @BeforeEach
  void setUp() {
    customerRepository.deleteAllInBatch();
    entityManager = emf.createEntityManager();
    transaction = entityManager.getTransaction();
  }

  private Customer persistCustomer() {
    transaction.begin();
    Customer customer = new Customer(1L, "first", "last");
    entityManager.persist(customer);
    transaction.commit();

    return customer;
  }

  @Test
  @DisplayName("고객을 저장한다.")
  void test_save_customer() {
    //given
    Customer customer = persistCustomer();

    //when then
    assertThat(customer).isNotNull();
  }

  @Test
  @DisplayName("고객을 DB에서 조회한다.")
  void test_find_customer_from_db() {
    //given
    Customer customer = persistCustomer();

    //when
    entityManager.detach(customer);
    Customer selectedCustomer = entityManager.find(Customer.class, 1L);

    //then
    assertThat(selectedCustomer).usingRecursiveComparison().isEqualTo(customer);
  }

  @Test
  @DisplayName("고객을 1차 캐시에서 조회한다.")
  void test_find_customer_from_cache() {
    //given
    Customer customer = persistCustomer();

    //when
    Customer selectedCustomer = entityManager.find(Customer.class, 1L);

    //then
    assertThat(selectedCustomer).usingRecursiveComparison().isEqualTo(customer);
  }

  @Test
  @DisplayName("영속성 컨텍스트에서 고객을 변경하면 save하지 않아도 변경사항이 DB에 반영된다.")
  void test_update_customer() {
    //given
    Customer customer = persistCustomer();

    //when
    transaction.begin();
    customer.updateFullName("new-first", "new-last");
    transaction.commit();

    //then
    Customer selectedCustomer = entityManager.find(Customer.class, 1L);
    assertThat(selectedCustomer).usingRecursiveComparison().isEqualTo(customer);
  }

  @Test
  @DisplayName("고객을 삭제한다.")
  void test_delete_customer() {
    //given
    Customer customer = persistCustomer();

    //when
    transaction.begin();
    entityManager.remove(customer);
    transaction.commit();

    //then
    Customer selectedCustomer = entityManager.find(Customer.class, 1L);
    assertThat(selectedCustomer).isNull();
  }

}