package com.kdt.module.customer.domain.repository;

import com.kdt.module.customer.domain.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerRepositoryCustomTest {
    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer = new Customer("hejow", "moon");

    @Test
    @DisplayName("save를 하면 Id값이 자동생성되고 값들이 정상적으로 저장되어야 한다.")
    void saveTest() {
        // given
        String firstName = "hejow";
        String lastName = "moon";

        // when
        Customer saved = customerRepository.save(customer);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getFirstName()).isEqualTo(firstName);
        assertThat(saved.getLastName()).isEqualTo(lastName);
    }

    @Test
    @DisplayName("id로 찾은 고객 정보가 저장된 정보와 같아야 한다.")
    void findByIdTest() {
        // given
        Customer saved = customerRepository.save(customer);

        // when
        Optional<Customer> optionalCustomer = customerRepository.findById(saved.getId());

        // then
        assertThat(optionalCustomer).isNotEmpty();
        Customer findCustomer = optionalCustomer.get();
        assertThat(findCustomer.getId()).isEqualTo(saved.getId());
    }

    @Test
    @DisplayName("고객 정보를 업데이트하면 정상적으로 반영되어야 한다.")
    void updateTest() {
        // given
        String newFirstName = "newFirstName";
        String newLastName = "newLastName";
        Customer saved = customerRepository.save(customer);

        // when
        customerRepository.update(saved.getId(), newFirstName, newLastName);

        // then
        Optional<Customer> optionalCustomer = customerRepository.findById(saved.getId());
        assertThat(optionalCustomer).isNotEmpty();

        Customer findCustomer = optionalCustomer.get();
        assertThat(findCustomer.getLastName()).isEqualTo(newLastName);
        assertThat(findCustomer.getFirstName()).isEqualTo(newFirstName);
    }

    @Test
    @DisplayName("삭제되면 아무것도 조회되지 않아야 한다.")
    void deleteByIdTest() {
        // given
        Customer saved = customerRepository.save(customer);

        // when
        customerRepository.deleteById(saved.getId());

        // then
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());
        assertThat(optionalCustomer).isEmpty();
    }
}
