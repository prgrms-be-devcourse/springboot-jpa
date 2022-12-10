package org.prgrms.devcoursejpa.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Test
    @DisplayName("회원이 성공적으로 저장된다.")
    void 회원_저장() {
        //given
        Customer customer = getCustomer();

        //when
        Customer savedOne = customerRepository.save(customer);
        long savedCount = customerRepository.count();

        //then
        assertEquals(customer, savedOne);
        assertEquals(1, savedCount);
    }

    @Test
    @DisplayName("회원의 이름이 성공적으로 변경된다.")
    void 회원_이름변경() {
        //given
        Customer customer = getCustomer();

        Customer savedCustomer = customerRepository.save(customer);

        assertEquals(customer.getFirstName(), savedCustomer.getFirstName());
        assertEquals(customer.getLastName(), savedCustomer.getLastName());

        //when
        customer.changeFirstName("Kiseo");
        Customer updatedCustomer = customerRepository.save(customer);

        //then
        assertEquals(updatedCustomer, customer);
        assertEquals("Kiseo", updatedCustomer.getFirstName());
        assertEquals("Kim", updatedCustomer.getLastName());
    }

    @Test
    @DisplayName("회원이 DB에서 성공적으로 삭제된다.")
    void 회원_삭제() {
        //given
        Customer customer = getCustomer();
        Customer savedCustomer = customerRepository.save(customer);

        //when
        customerRepository.deleteById(savedCustomer.getId());
        Optional<Customer> optionalCustomer = customerRepository.findById(savedCustomer.getId());

        //then
        assertTrue(optionalCustomer.isEmpty());
    }

    @Test
    @DisplayName("회원의 Id를 이용해 DB에서 조회할 수 있다.")
    void 회원_단건_조회() {
        //given
        Customer customer = getCustomer();
        Customer savedCustomer = customerRepository.save(customer);

        //when
        Optional<Customer> optionalCustomer = customerRepository.findById(savedCustomer.getId());

        assertTrue(optionalCustomer.isPresent());
        Customer findCustomer = optionalCustomer.get();

        //then
        assertEquals(savedCustomer, findCustomer);
    }

    @Test
    void 회원_전체_조회() {
        //given
        Customer customer1 = getCustomer();
        Customer customer2 = getCustomer();

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        //when
        List<Customer> customers = customerRepository.findAll();
        long savedCount = customerRepository.count();

        //then
        assertFalse(customers.isEmpty());
        assertEquals(2, savedCount);
        Assertions.assertThat(customers).contains(customer1, customer2);
    }

    private Customer getCustomer() {
        String firstName = "Giseo";
        String lastName = "Kim";

        return new Customer(firstName, lastName);
    }
}