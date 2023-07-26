package com.programmers.springbootjpa.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.springbootjpa.domain.Customer;
import com.programmers.springbootjpa.dto.CustomerCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void createCustomer() {
        CustomerCreateRequest request = new CustomerCreateRequest(
                1L,
                "Kim Jae Won",
                28,
                "hanjo",
                "서울시 마포구"
        );

        Customer savedCustomer = customerService.createCustomer(request);

        assertThat(savedCustomer).isEqualTo(Customer.of(request));
    }
}