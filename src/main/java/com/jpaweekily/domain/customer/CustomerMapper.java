package com.jpaweekily.domain.customer;

import com.jpaweekily.domain.customer.dto.CustomerRequest;
import com.jpaweekily.domain.customer.dto.CustomerResponse;

public class CustomerMapper {

    private CustomerMapper() {
    }

    public static CustomerResponse convertEntityToResponse(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getFirstName(), customer.getLastName());
    }

    public static Customer convertRequestToEntity(CustomerRequest request) {
        return Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();
    }
}
