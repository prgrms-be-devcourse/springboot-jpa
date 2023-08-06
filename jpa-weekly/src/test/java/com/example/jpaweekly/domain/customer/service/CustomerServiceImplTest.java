package com.example.jpaweekly.domain.customer.service;

import com.example.jpaweekly.domain.customer.dto.CustomerRequest;
import com.example.jpaweekly.domain.customer.dto.CustomerResponse;
import com.example.jpaweekly.domain.customer.dto.CustomerUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class CustomerServiceImplTest {
    @Autowired
    private CustomerService customerService;

    @Test
    void createTest() {
        // Given
        CustomerRequest customerRequest = new CustomerRequest("na", "yk");

        // When
        Long id = customerService.create(customerRequest);

        // Then
        assertThat(id).isNotNull();
    }

    @Test
    void findByIdTest() {
        CustomerRequest customerRequest = new CustomerRequest("na", "yk");
        Long id = customerService.create(customerRequest);
        //When
        CustomerResponse customerById = customerService.findCustomerById(id);
        //Then
        assertThat(customerById.id()).isEqualTo(id);
    }

    @Test
    void findAllTest() {
        //Given
        CustomerRequest customerRequest = new CustomerRequest("na", "yk");
        customerService.create(customerRequest);
        CustomerRequest customerRequest2 = new CustomerRequest("na", "nk");
        customerService.create(customerRequest2);
        //When
        List<CustomerResponse> customers = customerService.findCustomers();
        //Then
        assertThat(customers.size()).isEqualTo(2);
    }

    @Test
    void updateTest() {
        // Given
        CustomerRequest customerRequest = new CustomerRequest("na", "yk");
        Long id = customerService.create(customerRequest);
        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(id, "lee", "hk");
        // When
        CustomerResponse updated = customerService.update(customerUpdateRequest);
        // Then
        assertThat(customerUpdateRequest.firstName()).isEqualTo(updated.firstName());
        assertThat(customerUpdateRequest.lastName()).isEqualTo(updated.lastName());

    }

    @Test
    void deleteTest() {
        // Given
        CustomerRequest customerRequest = new CustomerRequest("na", "yk");
        Long id = customerService.create(customerRequest);
        // When
        customerService.delete(id);

        // Then
        assertThatThrownBy(()->customerService.findCustomerById(id))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
