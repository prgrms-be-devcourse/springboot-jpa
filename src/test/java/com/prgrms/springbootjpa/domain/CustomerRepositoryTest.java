package com.prgrms.springbootjpa.domain;

import com.prgrms.springbootjpa.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

  @Test
  @DisplayName("고객 엔티티 Create 테스트")
  void testCreate() {
    repository.save(customer);
    Optional<Customer> found = repository.findById(1L);
    assertThat(found.isPresent()).isTrue();
  }

  @Test
  @DisplayName("고객 엔티티 Delete 테스트")
  void testDelete() {
    repository.delete(customer);
    Optional<Customer> found = repository.findById(1L);
    assertThat(found.isEmpty()).isTrue();
  }
}