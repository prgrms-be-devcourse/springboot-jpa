package com.programmers.jpabasic.support;

import com.programmers.jpabasic.domain.customer.entity.Customer;
import com.programmers.jpabasic.domain.customer.entity.Name;

public class CustomerFixture {
    public static Customer create(String firstName, String lastName) {
        return Customer
            .builder()
            .name(Name.of(firstName, lastName))
            .build();
    }
}
