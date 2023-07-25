package me.kimihiqq.springbootjpa.domain.customer;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    static Customer customer;
    static Customer customer2;

    @BeforeAll
    public static void setUp() {
        customer = Customer.builder()
                .firstName("재석")
                .lastName("유")
                .build();
        customer2 = Customer.builder()
                .firstName("명수")
                .lastName("박")
                .build();
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    public void testSaveCustomer() {
        // Given
        Customer savedCustomer = customerRepository.save(customer);

        // Then
        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(savedCustomer.getFirstName()).isEqualTo("재석");
        assertThat(savedCustomer.getLastName()).isEqualTo("유");
    }

    @Transactional
    @Test
    public void testUpdateCustomerName() {
        // Given
        Customer savedCustomer = customerRepository.save(customer);

        // When
        savedCustomer.updateName("산슬", "유");

        // Then
        assertThat(savedCustomer.getFirstName()).isEqualTo("산슬");
        assertThat(savedCustomer.getLastName()).isEqualTo("유");
    }

    @Test
    public void testFindCustomerById() {
        // Given
        Customer savedCustomer = customerRepository.save(customer);

        // When
        Customer foundCustomer = customerRepository.findById(savedCustomer.getId()).orElse(null);

        // Then
        assertThat(foundCustomer).isNotNull();
        assertThat(foundCustomer.getId()).isEqualTo(savedCustomer.getId());
        assertThat(foundCustomer.getFirstName()).isEqualTo("재석");
        assertThat(foundCustomer.getLastName()).isEqualTo("유");
    }

    @Test
    public void testFindAllCustomers() {
        // Given
        Customer savedCustomer = customerRepository.save(customer);
        Customer savedCustomer2 = customerRepository.save(customer2);

        // When
        List<Customer> customers = customerRepository.findAll();

        // Then
        assertThat(customers).containsExactlyInAnyOrder(savedCustomer,savedCustomer2);
    }

    @Test
    public void testDeleteCustomerById() {
        // Given
        Customer savedCustomer = customerRepository.save(customer);

        // When
        customerRepository.deleteById(savedCustomer.getId());
        Customer deletedCustomer = customerRepository.findById(savedCustomer.getId()).orElse(null);

        // Then
        assertThat(deletedCustomer).isNull();
    }
}