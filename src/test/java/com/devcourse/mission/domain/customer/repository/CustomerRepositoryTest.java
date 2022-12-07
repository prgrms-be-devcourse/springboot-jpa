package com.devcourse.mission.domain.customer.repository;

import com.devcourse.mission.domain.customer.entity.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer getCustomer() {
        return Customer.builder()
                .id(1L)
                .age(20)
                .address("도시")
                .name("박현서")
                .build();
    }

    @Test
    void save_customer() {
        // given
        Customer customer = getCustomer();

        // when
        Customer savedCustomer = customerRepository.save(customer);

        // then
        assertThat(savedCustomer)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("age", 20)
                .hasFieldOrPropertyWithValue("address", "도시")
                .hasFieldOrPropertyWithValue("name", "박현서");
    }

    @Test
    void find_customer() {
        // given
        Customer customer = getCustomer();
        Customer savedCustomer = customerRepository.save(customer);

        // when
        Customer findCustomer = customerRepository.findById(savedCustomer.getId()).get();

        // then
        assertThat(findCustomer)
                .usingRecursiveComparison()
                .isEqualTo(savedCustomer);
    }

    @Test
    void update_customer() {
        // given
        Customer customer = getCustomer();
        Customer savedCustomer = customerRepository.save(customer);

        // when
        Customer updateCustomer = Customer.builder()
                .id(savedCustomer.getId())
                .age(3)
                .name("서현박")
                .address("시골")
                .build();

        Customer updatedCustomer = customerRepository.save(updateCustomer);
        Customer findCustomer = customerRepository.findById(savedCustomer.getId()).get();

        // then
        assertThat(findCustomer)
                .usingRecursiveComparison()
                .isEqualTo(updatedCustomer);
    }

    @Test
    void delete_customer() {
        // given
        Customer customer = getCustomer();
        Customer savedCustomer = customerRepository.save(customer);

        // when
        customerRepository.deleteById(savedCustomer.getId());
        Optional<Customer> mayBeCustomer = customerRepository.findById(savedCustomer.getId());

        // then
        assertThat(mayBeCustomer).isNotPresent();
    }
}