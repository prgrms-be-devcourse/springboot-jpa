package com.example.jpaweekly.domain.customer;

import com.example.jpaweekly.domain.customer.dto.CustomerRequest;
import com.example.jpaweekly.domain.customer.dto.CustomerResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomerMapper {

    public static CustomerResponse convertEntityToResponse(Customer customer){
        return new CustomerResponse(customer.getId(), customer.getFirstName(), customer.getLastName());
    }

    public static Customer convertRequestToEntity(CustomerRequest request){
        return Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();
    }
}
