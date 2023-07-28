package com.example.springbootjpa.customer.repository;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.springbootjpa.customer.model.Customer;

@DataJpaTest
class JpaCustomerRepositoryTest {

    @Autowired
    private JpaCustomerRepository jpaCustomerRepository;

    @Test
    @DisplayName("저장 기능 테스트")
    void saveTest() {
        // given
        Customer customer = new Customer(1L, "firstName", "lastName");

        // when
        Customer savedCustomer = jpaCustomerRepository.save(customer);

        // then
        assertEquals("firstName", savedCustomer.getFirstName());
    }

    @Test
    @DisplayName("단일 조회기능 테스트")
    void findByIdTest() {
        // given
        Customer customer = new Customer(1L, "firstName", "lastName");
        jpaCustomerRepository.save(customer);

        // when
        Optional<Customer> selectedCustomer = jpaCustomerRepository.findById(1L);

        // then
        assertEquals("firstName", selectedCustomer.get().getFirstName());
    }

    @Test
    @DisplayName("다건 조회기능 테스트")
    void findAllTest() {
        // given
        Customer customer1 = new Customer(1L, "firstFirstName", "firstLastName");
        Customer customer2 = new Customer(2L, "secondFirstName", "secondLastName");

        jpaCustomerRepository.save(customer1);
        jpaCustomerRepository.save(customer2);

        // when
        List<Customer> selectedCustomer = jpaCustomerRepository.findAll();

        // then
        assertEquals(2, selectedCustomer.size());
    }

    @Test
    @DisplayName("수정기능 테스트")
    void JpaCustomerRepositoryTest() {
        // given
        Customer customer = new Customer(1L, "firstName", "lastName");
        jpaCustomerRepository.save(customer);

        customer = jpaCustomerRepository.findById(1L).get();
        customer.setFirstName("editedFirstName");
        customer.setLastName("editedLastName");
        jpaCustomerRepository.save(customer);

        // when
        customer = jpaCustomerRepository.findById(1L).get();

        // then
        assertEquals("editedFirstName", customer.getFirstName());
    }

    @Test
    @DisplayName("삭제 테스트")
    void deleteTest() {
        // given
        Customer customer = new Customer(1L, "firstName","lastName");
        customer = jpaCustomerRepository.save(customer);
        assertTrue(jpaCustomerRepository.findById(1L).isPresent());

        // when
        jpaCustomerRepository.deleteById(1L);

        // then
        assertFalse(jpaCustomerRepository.findById(1L).isPresent());
    }


    
    
}