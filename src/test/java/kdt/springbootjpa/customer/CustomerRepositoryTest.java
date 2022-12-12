package kdt.springbootjpa.customer;

import kdt.springbootjpa.customer.entity.Customer;
import kdt.springbootjpa.customer.repository.CustomerRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("[성공] Customer 저장 및 조회하기")
    void testSaveAndGetCustomer() {
        Customer customer = Customer.builder().firstName("tlsdud").lastName("fortune").build();

        customerRepository.save(customer);
        Customer savedCustomer = customerRepository.findById(customer.getId()).get();

        assertThat(savedCustomer.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(savedCustomer.getLastName()).isEqualTo(customer.getLastName());
        assertThat(customerRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("[성공] Customer 수정하기")
    void testUpdateCustomer() {
        Customer customer = Customer.builder().firstName("sinyoung").lastName("bok").build();
        customerRepository.save(customer);

        String newFirstName = "tlsdud";
        String newLastName = "fortune";
        Customer savedCustomer = customerRepository.findById(customer.getId()).get();
        savedCustomer.setFirstName(newFirstName);
        savedCustomer.setLastName(newLastName);

        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();

        assertThat(updatedCustomer.getFirstName()).isEqualTo(newFirstName);
        assertThat(updatedCustomer.getLastName()).isEqualTo(newLastName);
        assertThat(customerRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("[성공] Customer 삭제하기")
    void testDeleteCustomer() {
        Customer customer = Customer.builder().firstName("sinyoung").lastName("bok").build();
        customerRepository.save(customer);
        Customer savedCustomer = customerRepository.findById(customer.getId()).get();

        customerRepository.delete(savedCustomer);

        assertThat(customerRepository.findAll().size()).isEqualTo(0);
    }
}
