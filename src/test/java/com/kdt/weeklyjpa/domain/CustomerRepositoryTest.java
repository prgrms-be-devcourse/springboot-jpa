package com.kdt.weeklyjpa.domain;

import com.kdt.weeklyjpa.domain.customer.Customer;
import com.kdt.weeklyjpa.domain.customer.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Customer_생성_테스트")
    void saveTest() {
        // given
        Customer customer = new Customer("Youngmyung", "Kim", "010-1234-5678");

        // when
        Customer saved = customerRepository.save(customer);
        Optional<Customer> foundById = customerRepository.findById(saved.getCustomerId());

        // then
        assertThat(foundById)
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }

    @Test
    @DisplayName("Customers_조회_테스트")
    void getCustomerTest() {
        // given
        Customer customer1 = new Customer("Youngmyung", "Kim", "010-1234-5678");
        Customer customer2 = new Customer("Test1", "Hong", "010-1111-2222");
        Customer customer3 = new Customer("Test2", "Kwon", "010-3333-4444");


        // when
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
        List<Customer> customers = customerRepository.findAll();

        // then
        assertThat(customers).hasSize(3);
    }

    @Test
    @DisplayName("Customer_update_테스트")
    void updateCustomerTest() {
        // given
        Customer customer = new Customer("Youngmyung", "Kim", "010-1234-5678");

        // when
        customerRepository.save(customer);
        Customer savedCustomer = customerRepository.findById(customer.getCustomerId()).get();
        savedCustomer.updateFirstName("NewFirstName");

        // then
        assertThat(customer.getFirstName()).isEqualTo("NewFirstName");
    }

    @Test
    @DisplayName("Customer_delete_테스트")
    void deleteCustomerTest() {
        // given
        Customer customer1 = new Customer("Youngmyung", "Kim", "010-1234-5678");
        Customer customer2 = new Customer("Test1", "Hong", "010-7777-9999");

        // when
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.deleteById(customer1.getCustomerId());
        List<Customer> customers = customerRepository.findAll();

        // then
        assertThat(customers).hasSize(1);
    }
}