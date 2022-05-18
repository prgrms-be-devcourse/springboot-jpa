package com.example.demo.converter;

import com.example.demo.domain.Customer;
import com.example.demo.dto.CustomerDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {
    public static Customer toCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();

        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        return customer;
    }

    public static CustomerDto toCustomerDto(Customer customer) {
        return new CustomerDto().builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .build();
    }
}
