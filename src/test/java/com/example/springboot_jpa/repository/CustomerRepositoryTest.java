package com.example.springboot_jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import com.example.springboot_jpa.entity.Customer;
import javax.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerRepositoryTest {

  private static final Customer customer;

  static {
    customer = new Customer();
    customer.setFirstName("Minsung");
    customer.setLastName("Kim");
    customer.setId(1L);
  }

  @Autowired
  private CustomerRepository repository;

  @AfterEach
  void tearDown() {
    repository.deleteAll();
  }

  @Test
  void can_create_customer() {

    // Given

    // When
    repository.save(customer);

    // Then
    var savedCustomer = repository.findById(1L).orElseThrow();
    assertThat(savedCustomer.getId()).isEqualTo(customer.getId());
    assertThat(savedCustomer.getFirstName()).isEqualTo(customer.getFirstName());
    assertThat(savedCustomer.getLastName()).isEqualTo(customer.getLastName());
  }

  @Test
  void update_customers_by_saving_same_id_entity() {

    // Given
    repository.save(customer);
    var duplicatedCustomer = new Customer(1L, "duplicated", "duplicated!");
    // When
    repository.save(duplicatedCustomer);

    // Then
    assertThat(repository.findAll()).hasSize(1);
    assertThat(repository.findById(customer.getId())).isNotEmpty().get()
        .isEqualTo(duplicatedCustomer);
  }

  @ParameterizedTest
  @CsvSource({"yaho,bro", "minsu,kim"})
  @Transactional
  void update_customers_by_setter_using_transaction(String firstName, String lastName) {

    // Given
    repository.save(customer);
    var savedCustomer = repository.findById(customer.getId()).orElseThrow();

    // When
    savedCustomer.setFirstName(firstName);
    savedCustomer.setLastName(lastName);
    // Then
    var updatedCustomer = repository.findById(savedCustomer.getId()).orElseThrow();
    assertThat(updatedCustomer.getFirstName()).isEqualTo(firstName);
    assertThat(updatedCustomer.getLastName()).isEqualTo(lastName);
  }

  @Test
  void delete_test() {

    // Given
    repository.save(customer);

    // When
    repository.delete(customer);

    // Then
    assertThat(repository.count()).isZero();
    assertThat(repository.findAll()).isEmpty();
  }

  @Test
  void delete_empty_repository_does_not_throw_any_error() {
    assertThatNoException().isThrownBy(() -> {
      repository.delete(customer);
      repository.delete(customer);
      repository.delete(customer);
    });
  }

  @Test
  void can_select_all_customers_in_database() {

    // Given
    repository.save(customer);
    var secondCustomer = new Customer(2, "mansu", "salad");
    repository.save(secondCustomer);

    // When
    var savedCustomers = repository.findAll();

    // Then
    assertThat(savedCustomers).hasSize(2).contains(customer, secondCustomer);
  }

}


