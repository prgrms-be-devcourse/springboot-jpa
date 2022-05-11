package com.kdt.lecture.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kdt.lecture.domain.Customer;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CustomerRepositoryTest {

  @Autowired
  private CustomerRepository customerRepository;

  private Customer customer() {
    return new Customer(1L, "first", "last");
  }

  @Test
  @DisplayName("고객을 추가한다.")
  void test_create_customer() {
    //given
    Customer customer = customer();

    //when
    Customer savedCustomer = customerRepository.save(customer);
    Optional<Customer> maybeCustomer = customerRepository.findById(1L);

    //then
    assertThat(maybeCustomer).isPresent();
    assertThat(maybeCustomer.get()).isEqualTo(savedCustomer);
  }

  @Test
  @DisplayName("고객을 ID로 조회한다.")
  void test_select_customer_by_id() {
    //given
    Customer customer = customer();
    Customer savedCustomer = customerRepository.save(customer);

    //when
    Optional<Customer> maybeCustomer = customerRepository.findById(1L);

    //then
    assertThat(maybeCustomer).isPresent();
    assertThat(maybeCustomer.get()).usingRecursiveComparison().isEqualTo(savedCustomer);
  }

  @Test
  @DisplayName("모든 고객을 조회한다.")
  void test_all_customer() {
    //given
    Customer newCustomer = new Customer(2L, "second", "new");
    List<Customer> customers = List.of(customer(), newCustomer);
    customerRepository.saveAll(customers);

    //when
    List<Customer> allCustomers = customerRepository.findAll();

    //then
    assertThat(allCustomers).hasSize(2);
  }

  @Test
  @DisplayName("고객 전체 이름을 수정한다.")
  void test_update_customer() {
    //given
    Customer customer = customer();
    Customer savedCustomer = customerRepository.save(customer);

    //when
    customer.updateFullName("updated-first", "updated-last");
    Optional<Customer> maybeCustomer = customerRepository.findById(customer.getId());

    //then
    assertThat(maybeCustomer).isPresent();
    assertThat(maybeCustomer.get()).usingRecursiveComparison().isEqualTo(savedCustomer);
  }

  @Test
  @DisplayName("고객을 삭제한다.")
  void test_delete_customer() {
    //given
    Customer customer = customer();
    Customer savedCustomer = customerRepository.save(customer);

    //when
    customerRepository.delete(savedCustomer);
    Optional<Customer> maybeCustomer = customerRepository.findById(1L);

    //then
    assertThat(maybeCustomer).isEmpty();
  }
}