package org.programmers.jpaweeklymission.customer.application;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.programmers.jpaweeklymission.customer.Customer;
import org.programmers.jpaweeklymission.customer.application.dto.CustomerResponse;
import org.programmers.jpaweeklymission.customer.presentation.dto.CustomerCreationRequest;
import org.programmers.jpaweeklymission.customer.presentation.dto.CustomerUpdateRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomerMapper {
    public static CustomerResponse toResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .build();
    }

    public static Customer toCustomer(CustomerCreationRequest request) {
        return Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();
    }

    public static Customer toCustomer(CustomerUpdateRequest request) {
        return Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();
    }
}
