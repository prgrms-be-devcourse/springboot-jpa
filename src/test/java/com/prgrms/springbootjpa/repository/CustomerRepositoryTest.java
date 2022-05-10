package com.prgrms.springbootjpa.repository;

import com.prgrms.springbootjpa.domain.Customer;
import com.prgrms.springbootjpa.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

  @Autowired
  private CustomerRepository repository;

  private static Customer customer;

  @BeforeEach
  void beforeEach() {
    customer = new Customer();
    customer.setId(1L);
    customer.setFirstName("first");
    customer.setLastName("last");
  }

  @AfterEach
  void afterEach() {
    repository.deleteAll();
  }

  @Test
  @DisplayName("고객 엔티티 Create 테스트")
  void testCreate() {
    repository.save(customer);
    Optional<Customer> found = repository.findById(1L);
    assertThat(found.isPresent()).isTrue();
  }

  @Test
  @DisplayName("고객 엔티티 Read 테스트")
  void testRead() {
    repository.save(customer);
    Customer found = repository.findById(1L).get();
    assertThat(found.getId()).isEqualTo(customer.getId());
    assertThat(found.getFirstName()).isEqualTo(customer.getFirstName());
    assertThat(found.getLastName()).isEqualTo(customer.getLastName());
  }

  @Test
  @DisplayName("고객 엔티티 Update 테스트")
  void testUpdate() {
    repository.save(customer);

    String newLastName = "new";
    Optional<Customer> found = repository.findById(1L);
    found.ifPresent(updateCustomer -> {
      updateCustomer.setLastName(newLastName);
      repository.save(updateCustomer);
    });

    found = repository.findById(1L);
    assertThat(found.get().getLastName()).isEqualTo(newLastName);
  }

  @Test
  @DisplayName("고객 엔티티 Delete 테스트")
  void testDelete() {
    repository.delete(customer);
    Optional<Customer> found = repository.findById(1L);
    assertThat(found.isEmpty()).isTrue();
  }
}