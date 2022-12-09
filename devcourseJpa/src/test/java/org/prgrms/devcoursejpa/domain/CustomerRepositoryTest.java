package org.prgrms.devcoursejpa.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Test
    @DisplayName("회원이 성공적으로 저장된다.")
    void 회원_저장() {
        Customer customer = new Customer("Giseo", "Kim");

        Customer savedOne = customerRepository.save(customer);

        assertEquals(customer, savedOne);
    }

    @Test
    @DisplayName("회원의 이름이 성공적으로 변경된다.")
    void updateTest() {
        String firstName = "Giseo";
        String lastName = "Kim";

        Customer customer = new Customer(firstName, lastName);
        Customer savedOne = customerRepository.save(customer);

        assertEquals(firstName, savedOne.getFirstName());
        assertEquals(lastName, savedOne.getLastName());

        customer.changeFirstName("Kiseo");
        Customer updatedCustomer = customerRepository.save(customer);

        assertEquals(updatedCustomer, customer);
        assertEquals("Kiseo", updatedCustomer.getFirstName());
        assertEquals("Kim", updatedCustomer.getLastName());
    }

    @Test
    void deleteTest() {
        String firstName = "Giseo";
        String lastName = "Kim";

        Customer customer = new Customer(firstName, lastName);
        Customer savedOne = customerRepository.save(customer);

        customerRepository.deleteById(savedOne.getId());
        Optional<Customer> optionalCustomer = customerRepository.findById(savedOne.getId());

        assertTrue(optionalCustomer.isEmpty());
    }
}