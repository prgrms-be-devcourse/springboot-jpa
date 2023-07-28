package com.example.springbootjpa.mission1.repository;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.springbootjpa.mission1.customer.model.Customer;
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
        assertEquals("firstName", savedCustomer.getFirstName());
    }

    @Test
    @DisplayName("고객 단일 조회기능 테스트")
    void findByIdTest() {
        // given
        Customer customer = new Customer(1L, "firstName", "lastName");
        customerRepository.save(customer);

        // when
        Optional<Customer> selectedCustomer = customerRepository.findById(1L);

        // then
        assertEquals("firstName", selectedCustomer.get().getFirstName());
    }

    @Test
    @DisplayName("고객 다건 조회기능 테스트")
    void findAllTest() {
        // given
        Customer customer1 = new Customer(1L, "firstFirstName", "firstLastName");
        Customer customer2 = new Customer(2L, "secondFirstName", "secondLastName");

        customerRepository.save(customer1);
        customerRepository.save(customer2);

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
        customer.setFirstName("editedFirstName");
        customer.setLastName("editedLastName");
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