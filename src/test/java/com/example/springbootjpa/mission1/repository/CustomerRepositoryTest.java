package com.example.springbootjpa.mission1.repository;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.springbootjpa.mission1.customer.domain.Customer;
import com.example.springbootjpa.mission1.customer.repository.CustomerRepository;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("고객 저장 기능 테스트")
    void saveTest() {
        // given
        Customer customer = new Customer(1L, "firstName", "lastName");

        // when
        Customer savedCustomer = customerRepository.save(customer);

        // then
        assertThat(savedCustomer).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @DisplayName("고객 단일 조회기능 테스트")
    void findByIdTest() {
        // given
        Customer customer = new Customer(1L, "firstName", "lastName");
        customer = customerRepository.save(customer);

        // when
        Optional<Customer> selectedCustomer = customerRepository.findById(customer.getId());

        // then
        assertEquals("firstName", selectedCustomer.get().getFirstName());
    }

    @Test
    @DisplayName("고객 다건 조회기능 테스트")
    void findAllTest() {
        // given
        Customer customer1 = new Customer(1L, "firstFirstName", "firstLastName");
        Customer customer2 = new Customer(2L, "secondFirstName", "secondLastName");

        customerRepository.saveAll(Arrays.asList(customer1, customer2));

        // when
        List<Customer> selectedCustomer = customerRepository.findAll();

        // then
        assertEquals(2, selectedCustomer.size());
    }

    @Test
    @DisplayName("고객 수정기능 테스트")
    void JpaCustomerRepositoryTest() {
        // given
        Customer customer = new Customer(1L, "firstName", "lastName");
        customerRepository.save(customer);

        customer = customerRepository.findById(1L).get();
        customer.update("editedFirstName", "editedLastName");
        customerRepository.save(customer);

        // when
        customer = customerRepository.findById(1L).get();

        // then
        assertEquals("editedFirstName", customer.getFirstName());
    }

    @Test
    @DisplayName("고객 삭제 테스트")
    void deleteTest() {
        // given
        Customer customer = new Customer(1L, "firstName", "lastName");
        assertTrue(customerRepository.findById(1L).isPresent());

        // when
        customerRepository.deleteById(1L);

        // then
        assertFalse(customerRepository.findById(1L).isPresent());
    }

}