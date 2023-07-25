package com.example.jpaweekly.domain.customer.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.jpaweekly.domain.customer.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CustomerRepositoryTest {

  @Autowired
  CustomerRepository customerRepository;
  private Customer customer;

  @BeforeEach
  void setUp() {
    customer = Customer.builder()
        .firstName("준일")
        .lastName("배")
        .build();
  }

  @AfterEach
  void tearDown() {
    customerRepository.deleteAll();
  }

  @Test
  @DisplayName("성공 : 고객 저장")
  void saveCustomer() {
    //given

    // when
    Customer savedCustomer = customerRepository.save(customer);

    // then
    assertThat(savedCustomer.getFirstName()).isEqualTo(customer.getFirstName());
    assertThat(savedCustomer.getLastName()).isEqualTo(customer.getLastName());
  }

  @Test
  @DisplayName("성공 : 고객 성 수정")
  void updateCustomerLastName() {
    //given
    Customer savedCustomer = customerRepository.save(customer);
    Customer foundCustomer = customerRepository.findById(savedCustomer.getId()).get();
    final String updatedLastName = "김";

    // when
    foundCustomer.updateLastName(updatedLastName);

    // then
    assertThat(foundCustomer.getLastName()).isEqualTo(updatedLastName);
  }

  @Test
  @DisplayName("성공 : 고객 이름 수정")
  void updateCustomerFirstName() {
    //given
    Customer savedCustomer = customerRepository.save(customer);
    Customer foundCustomer = customerRepository.findById(savedCustomer.getId()).get();
    final String updatedLastName = "준영";

    // when
    foundCustomer.updateFirstName(updatedLastName);

    // then
    assertThat(foundCustomer.getFirstName()).isEqualTo(updatedLastName);
  }

  @Test
  @DisplayName("성공 : 고객 조회")
  void getCustomer() {
    //given
    Customer savedCustomer = customerRepository.save(customer);

    // when
    Customer foundCustomer = customerRepository.findById(savedCustomer.getId()).get();

    // then
    assertThat(savedCustomer.getId()).isEqualTo(foundCustomer.getId());
  }

  @Test
  @DisplayName("성공 : 고객 삭제")
  void deleteCustomer() {
    //given
    Customer savedCustomer = customerRepository.save(customer);

    // when
    customerRepository.deleteById(savedCustomer.getId());

    // then
    boolean foundCustomer = customerRepository.existsById(savedCustomer.getId());
    assertThat(foundCustomer).isFalse();
  }
}