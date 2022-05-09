package com.programmers.jpa.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void deleteEach() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("저장 테스트")
    void saveTest() {
        Customer customer = new Customer(1L, "hanju", "lee");

        customerRepository.save(customer);

        Customer savedCustomer = customerRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디"));

        assertAll(
                () -> assertThat(savedCustomer.getId()).isEqualTo(customer.getId()),
                () -> assertThat(savedCustomer.getFirstName()).isEqualTo(customer.getFirstName()),
                () -> assertThat(savedCustomer.getLastName()).isEqualTo(customer.getLastName())
        );
    }

    @Test
    @DisplayName("중복 id로 저장할 때 한 개만 저장 테스트")
    void saveDuplicateIdTest() {
        Customer customer = new Customer(1L, "hanju", "lee");
        Customer customer2 = new Customer(1L, "hanju", "lee");
        Customer customer3 = new Customer(1L, "hanju", "lee");

        customerRepository.save(customer);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(1);
    }

    @Test
    @DisplayName("업데이트 테스트")
    void updateTest() {
        Customer customer = new Customer(1L, "hanju", "lee");
        customerRepository.save(customer);

        customer.setLastName("kim");
        customerRepository.save(customer);

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers).hasSize(1);
        assertThat(customers.get(0).getLastName()).isEqualTo("kim");
    }

    @Test
    @DisplayName("전체 조회 테스트")
    void findAllTest() {
        Customer customer = new Customer(1L, "hanju", "lee");
        Customer customer2 = new Customer(2L, "hanju", "lee");
        Customer customer3 = new Customer(3L, "hanju", "lee");

        customerRepository.save(customer);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        List<Customer> customers = customerRepository.findAll();

        assertAll(
                () -> assertThat(customers).hasSize(3),
                () -> assertThat(customers.get(0).getId()).isEqualTo(customer.getId()),
                () -> assertThat(customers.get(0).getFirstName()).isEqualTo(customer.getFirstName()),
                () -> assertThat(customers.get(0).getLastName()).isEqualTo(customer.getLastName())
        );
    }

    @Test
    @DisplayName("Id로 조회")
    void findByIdTest() {
        Customer customer = new Customer(1L, "hanju", "lee");
        Customer savedCustomer = customerRepository.save(customer);

        Customer findCustomer = customerRepository.findById(savedCustomer.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디"));

        assertAll(
                () -> assertThat(findCustomer.getId()).isEqualTo(customer.getId()),
                () -> assertThat(findCustomer.getFirstName()).isEqualTo(customer.getFirstName()),
                () -> assertThat(findCustomer.getLastName()).isEqualTo(customer.getLastName())
        );
    }

    @Test
    @DisplayName("전체 삭제")
    void deleteAllTest() {
        Customer customer = new Customer(1L, "hanju", "lee");
        Customer customer2 = new Customer(2L, "hanju", "lee");
        Customer customer3 = new Customer(3L, "hanju", "lee");

        customerRepository.save(customer);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        customerRepository.deleteAll();

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers).isEmpty();
    }

    @Test
    @DisplayName("Id로 삭제")
    void deleteById() {
        Customer customer = new Customer(1L, "hanju", "lee");
        Customer customer2 = new Customer(2L, "hanju", "lee");
        Customer customer3 = new Customer(3L, "hanju", "lee");

        customerRepository.save(customer);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        customerRepository.deleteById(customer.getId());

        Optional<Customer> findCustomer = customerRepository.findById(customer.getId());
        List<Customer> customers = customerRepository.findAll();

        assertAll(
                () -> assertThat(findCustomer).isNotPresent(),
                () -> assertThat(customers).hasSize(2)
        );
    }


}