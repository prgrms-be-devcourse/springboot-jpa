package com.example.springboojpa;


import com.example.springboojpa.domain.customer.Customer;
import com.example.springboojpa.domain.customer.CustomerRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    void initCustomer() {
        customer = new Customer(1L,"kim","yongsang");
    }

    @Test
    @DisplayName("save() 실행을 확인한다.")
    void save() {
        // Given
        // When
        customerRepository.save(customer);

        // Then
        Customer findCustomer = customerRepository.findById(1L).get();
        assertThat(findCustomer.getId()).isEqualTo(1L);
        assertThat(findCustomer.getFirstName()).isEqualTo(customer.getFirstName());
    }

    @Test
    @DisplayName("변경감지로 수정이 잘 되는지 확인한다")
    void update(){
        // Given
        Customer entity = customerRepository.save(customer);

        // When
        entity.setFirstName("Leonel");
        entity.setLastName("Messi");

        // Then
        Customer findCustomer = customerRepository.findById(1L).get();
        assertThat(findCustomer.getId()).isEqualTo(1L);
        assertThat(findCustomer.getFirstName()).isEqualTo(entity.getFirstName());
        assertThat(findCustomer.getLastName()).isEqualTo(entity.getLastName());
    }

    @Test
    @DisplayName("findById() 실행을 확인한다.")
    void findById() {
        // Given
        customerRepository.save(customer);

        // When
        Customer selected = customerRepository.findById(customer.getId()).get();

        // Then
        assertThat(customer.getId()).isEqualTo(selected.getId());
    }

    @Test
    void 리스트조회를_확인한다() {
        // Given
        Customer customer2 = new Customer(2L,"lee","jaeyong");

        customerRepository.save(customer);
        customerRepository.save(customer2);

        // When
        List<Customer> findCustomers = customerRepository.findAll();

        // Then
        assertThat(findCustomers.size()).isEqualTo(2);
    }
}
