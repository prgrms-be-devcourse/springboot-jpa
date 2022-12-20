package kdt.springbootjpa.customer;

import kdt.springbootjpa.customer.entity.Customer;
import kdt.springbootjpa.customer.repository.CustomerRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("[성공] Customer 저장 및 조회하기")
    void testSaveAndGetCustomer() {
        Customer customer = Customer.builder().firstName("tlsdud").lastName("fortune").build();
        customerRepository.save(customer);

        Optional<Customer> savedCustomer = customerRepository.findById(customer.getId());
        assertThat(savedCustomer)
                .hasValueSatisfying(customer1 -> assertAll(
                        () -> assertThat(customer1.getFirstName()).isEqualTo("tlsdud"),
                        () -> assertThat(customer1.getLastName()).isEqualTo("fortune")
                ));
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(1);
    }

    @Test
    @DisplayName("[성공] Customer 수정하기")
    void testUpdateCustomer() {
        Customer customer = Customer.builder().firstName("sinyoung").lastName("bok").build();
        customerRepository.save(customer);

        String newFirstName = "tlsdud";
        String newLastName = "fortune";
        Customer savedCustomer = customerRepository.findById(customer.getId()).get();
        savedCustomer.changeFirstName(newFirstName);
        savedCustomer.changeLastName(newLastName);

        Optional<Customer> updatedCustomer = customerRepository.findById(customer.getId());
        assertThat(updatedCustomer)
                .hasValueSatisfying(customer1 -> assertAll(
                        () -> assertThat(customer1.getFirstName()).isEqualTo(newFirstName),
                        () -> assertThat(customer1.getLastName()).isEqualTo(newLastName)
                ));
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(1);
    }

    @Test
    @DisplayName("[성공] Customer 삭제하기")
    void testDeleteCustomer() {
        Customer customer = Customer.builder().firstName("sinyoung").lastName("bok").build();
        customerRepository.save(customer);

        Customer savedCustomer = customerRepository.findById(customer.getId()).get();
        customerRepository.delete(savedCustomer);

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).isEmpty();
    }
}
