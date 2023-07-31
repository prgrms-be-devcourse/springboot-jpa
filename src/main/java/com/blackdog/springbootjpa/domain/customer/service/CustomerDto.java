package com.blackdog.springbootjpa.domain.customer.service;

import com.blackdog.springbootjpa.domain.customer.dto.CustomerUpdateRequest;
import com.blackdog.springbootjpa.domain.customer.vo.Age;
import com.blackdog.springbootjpa.domain.customer.vo.Email;
import com.blackdog.springbootjpa.domain.customer.vo.Name;
import lombok.Builder;


@Builder
public record CustomerDto(
        Name name,
        Age age,
        Email email
) {
    public static CustomerDto toDto(CustomerUpdateRequest updateRequest) {
        return CustomerDto.builder()
                .name(new Name(updateRequest.name()))
                .age(new Age(updateRequest.age()))
                .email(new Email(updateRequest.email()))
                .build();
    }
}
