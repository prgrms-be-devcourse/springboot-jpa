package org.programmers.jpaweeklymission.customer.application;

import org.programmers.jpaweeklymission.customer.presentation.dto.CustomerCreationRequest;
import org.programmers.jpaweeklymission.customer.application.dto.CustomerResponse;
import org.programmers.jpaweeklymission.customer.Customer;

public final class CustomerMapper {
    private CustomerMapper() {

    }

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
}
