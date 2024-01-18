package com.example.kdtjpa.domain.customer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;
    private Customer customer;

    @BeforeEach
    @DisplayName("고객을 생성합니다.")
    void setUp() {
        customerRepository.deleteAll();

        //Given
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Eugene");
        customer.setLastName("Park");

        //When
        Customer savedCustomer = customerRepository.save(customer);

        //Then
        assertThat(customer.getId()).isEqualTo(savedCustomer.getId());
    }

    @Test
    @DisplayName("전체 고객을 조회합니다.")
    void find_all() {
        //Given
        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Eugene2");
        customer2.setLastName("Park2");
        customerRepository.save(customer2);

        //When
        List<Customer> all = customerRepository.findAll();

        //Then
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("고객을 id로 조회합니다.")
    void find_by_id() {
        //When
        Customer findCustomer = customerRepository.findById(customer.getId()).get();

        //Then
        assertThat(findCustomer.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(findCustomer.getLastName()).isEqualTo(customer.getLastName());
    }

    @Test
    @DisplayName("고객정보를 수정 합니다")
    void update() {
        //When
        customer.setFirstName("update Eugene");
        customer.setLastName("update Park");
        Customer updatedCustomer = customerRepository.save(customer);

        //Then
        assertThat(customer.getFirstName()).isEqualTo(updatedCustomer.getFirstName());
        assertThat(customer.getLastName()).isEqualTo(updatedCustomer.getLastName());
    }

    @Test
    @DisplayName("고객을 삭제 합니다")
    void delete() {
        //When
        customerRepository.delete(customer);

        //Then
        assertThat(customerRepository.findAll().size()).isEqualTo(0);
    }
}
