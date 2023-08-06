package com.programmers.week.customer.presentation;

import com.programmers.week.customer.domain.Customer;

public record CustomerResponse(Long id, String firstName, String lastName) {
  public static CustomerResponse from(Customer customer) {
    return new CustomerResponse(
            customer.getId(),
            customer.getFirstName(),
            customer.getLastName()
    );
  }

}
