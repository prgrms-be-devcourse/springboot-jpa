package com.example.jpaweekly.domain.customer.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.jpaweekly.domain.customer.Customer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CustomerRepositoryTest {

  @Autowired
  private CustomerRepository customerRepository;
  private Customer customer;
  private Customer savedCustomer;
  private Validator validator;

  @BeforeEach
  void setUp() {
    customer = Customer.builder()
        .firstName("준일")
        .lastName("배")
        .build();

    savedCustomer = customerRepository.save(customer);

    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @AfterEach
  void tearDown() {
    customerRepository.deleteAll();
  }

  @Test
  @DisplayName("성공 : 고객 저장")
  void saveCustomer() {
    // given

    // when

    // then
    assertThat(savedCustomer.getFirstName()).isEqualTo(customer.getFirstName());
    assertThat(savedCustomer.getLastName()).isEqualTo(customer.getLastName());
  }

  @Test
  @DisplayName("예외: firstName에 한글이나 영어가 아닌 문자 입력")
  void firstName_InvalidCharacters_ThrowsConstraintViolationException() {
    // given
    Customer invalidCustomer = Customer.builder()
        .firstName("준1")
        .lastName("배")
        .build();

    // when
    Set<ConstraintViolation<Customer>> violations = validator.validate(invalidCustomer);

    //then
    assertThat(violations).hasSize(1);
  }

  @Test
  @DisplayName("예외: lastName에 한글이나 영어가 아닌 문자 입력")
  void lastName_InvalidCharacters_ThrowsConstraintViolationException() {
    // given
    Customer invalidCustomer = Customer.builder()
        .firstName("준일")
        .lastName("ㅂㅏ1")
        .build();

    // when
    Set<ConstraintViolation<Customer>> violations = validator.validate(invalidCustomer);

    //then
    assertThat(violations).hasSize(1);
  }

  @Test
  @DisplayName("성공 : 고객 성 수정")
  void updateCustomerLastName() {
    //given
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

    // when
    Customer foundCustomer = customerRepository.findById(savedCustomer.getId()).get();

    // then
    assertThat(savedCustomer.getId()).isEqualTo(foundCustomer.getId());
  }

  @Test
  @DisplayName("성공 : 고객 삭제")
  void deleteCustomer() {
    //given

    // when
    customerRepository.deleteById(savedCustomer.getId());

    // then
    boolean foundCustomer = customerRepository.existsById(savedCustomer.getId());
    assertThat(foundCustomer).isFalse();
  }
}