package com.blackdog.springbootjpa.domain.customer.service;

import com.blackdog.springbootjpa.domain.customer.dto.CustomerCreateRequest;
import com.blackdog.springbootjpa.domain.customer.dto.CustomerUpdateRequest;
import com.blackdog.springbootjpa.domain.customer.model.Customer;
import com.blackdog.springbootjpa.domain.customer.vo.Age;
import com.blackdog.springbootjpa.domain.customer.vo.CustomerName;
import com.blackdog.springbootjpa.domain.customer.vo.Email;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {

    public Customer toEntity(CustomerCreateRequest customerCreateRequest) {
        return Customer.builder()
                .customerName(new CustomerName(customerCreateRequest.name()))
                .age(new Age(customerCreateRequest.age()))
                .email(new Email(customerCreateRequest.email()))
                .build();
    }

    public Customer toEntity(CustomerUpdateRequest customerUpdateRequest) {
        return Customer.builder()
                .customerName(new CustomerName(customerUpdateRequest.name()))
                .age(new Age(customerUpdateRequest.age()))
                .email(new Email(customerUpdateRequest.email()))
                .build();
    }

}
