package com.example.springboot_jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springboot_jpa.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerRepositoryTest {

  @Autowired
  private CustomerRepository repository;

  @Test
  void save_customer_in_repository_working() {

    // Given
    var customer = new Customer();
    customer.setFirstName("Minsung");
    customer.setLastName("Kim");
    customer.setId(1L);

    // When
    repository.save(customer);

    // Then
    var savedCustomer = repository.findById(1L).orElseThrow();
    assertThat(savedCustomer.getId()).isEqualTo(customer.getId());
    assertThat(savedCustomer.getFirstName()).isEqualTo(customer.getFirstName());
    assertThat(savedCustomer.getLastName()).isEqualTo(customer.getLastName());
  }

}
