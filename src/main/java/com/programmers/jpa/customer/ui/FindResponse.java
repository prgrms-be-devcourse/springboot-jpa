package com.programmers.jpa.customer.ui;

import com.programmers.jpa.customer.domain.Customer;

public record FindResponse(Long id, String firstName, String lastName) {
    public static FindResponse from(Customer customer) {
        return new FindResponse(customer.getId(), customer.getFirstName(), customer.getLastName());
    }
}
