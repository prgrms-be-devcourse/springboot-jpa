package com.blackdog.springbootjpa.domain.customer.dto;

import com.blackdog.springbootjpa.domain.customer.model.Customer;
import lombok.Builder;

@Builder
public record CustomerResponse(
        long id,
        String name,
        int age,
        String email
) {
    public static CustomerResponse toDto(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getCustomerNameValue())
                .age(customer.getAgeValue())
                .email(customer.getEmailAddress())
                .build();
    }
}
