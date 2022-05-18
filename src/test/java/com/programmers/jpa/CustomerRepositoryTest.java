package com.programmers.jpa;

import com.programmers.jpa.domain.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    @DisplayName("Customer를 저장한다.")
    void saveTest() {
        // Given
        Customer customer = Customer.of("haemin", "jeong");

        // When
        customerRepository.save(customer);

        // Then
        Optional<Customer> findCustomer = customerRepository.findById(customer.getId());
        assertThat(findCustomer).isNotEmpty();
        assertThat(findCustomer.get()).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("Customer를 ID로 조회한다.")
    void readTest() {
        // Given
        Customer customer = Customer.of("haemin", "jeong");
        customerRepository.save(customer);

        // When
        Optional<Customer> findCustomer = customerRepository.findById(customer.getId());

        // Then
        assertThat(findCustomer).isNotEmpty();
        assertThat(findCustomer.get()).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("존재하지 않는 ID로 Customer를 조회하면 Optional.empty()가 반환된다.")
    void readFailTest() {
        // Given
        Customer customer = Customer.of("haemin", "jeong");
        customerRepository.save(customer);
        long wrongId = -1L;

        // When
        Optional<Customer> findCustomer = customerRepository.findById(wrongId);

        // Then
        assertThat(findCustomer).isEmpty();
    }

    @Test
    @DisplayName("모든 Customer를 조회한다.")
    void readAllTest() {
        // Given
        Customer customer1 =  Customer.of("haemin", "jeong");
        Customer customer2 =  Customer.of("hongu", "kang");
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        // When
        List<Customer> customers = customerRepository.findAll();

        // Then
        assertThat(customers.size()).isEqualTo(2);
        assertThat(customers).contains(customer1, customer2);
    }

    @Test
    @DisplayName("Customer의 이름을 업데이트한다.")
    void updateTest() {
        // Given
        Customer customer = Customer.of("haemin", "jeong");
        customerRepository.save(customer);

        // When
        customer.changeName("hongu", "kang");

        // Then
        Optional<Customer> findCustomer = customerRepository.findById(customer.getId());
        assertThat(findCustomer).isNotEmpty();
        assertThat(findCustomer.get()).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("Customer를 삭제한다.")
    void deleteTest() {
        // Given
        Customer customer = Customer.of("haemin", "jeong");
        customerRepository.save(customer);

        // When
        customerRepository.delete(customer);

        // Then
        Optional<Customer> deletedCustomer = customerRepository.findById(customer.getId());
        assertThat(deletedCustomer).isEmpty();
    }
}