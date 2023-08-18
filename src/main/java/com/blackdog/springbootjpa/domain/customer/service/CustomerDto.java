package com.blackdog.springbootjpa.domain.customer.service;

import com.blackdog.springbootjpa.domain.customer.dto.CustomerUpdateRequest;
import com.blackdog.springbootjpa.domain.customer.vo.Age;
import com.blackdog.springbootjpa.domain.customer.vo.CustomerName;
import com.blackdog.springbootjpa.domain.customer.vo.Email;
import lombok.Builder;


@Builder
public record CustomerDto(
        CustomerName customerName,
        Age age,
        Email email
) {
    public static CustomerDto toDto(CustomerUpdateRequest updateRequest) {
        return CustomerDto.builder()
                .customerName(new CustomerName(updateRequest.name()))
                .age(new Age(updateRequest.age()))
                .email(new Email(updateRequest.email()))
                .build();
    }
}
