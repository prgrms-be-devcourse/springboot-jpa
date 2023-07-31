package com.blackdog.springbootjpa.domain.customer.dto;

import com.blackdog.springbootjpa.domain.customer.model.Customer;
import lombok.Builder;

@Builder
public record CustomerResponse(
        int id,
        String name,
        int age,
        String email
) {
    public static CustomerResponse toDto(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName().getNameValue())
                .age(customer.getAge().getAgeValue())
                .email(customer.getEmail().getEmailAddress())
                .build();
    }
}
