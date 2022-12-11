package com.example.springbootpart4.domain;

import org.junit.jupiter.api.Test;

public class LombokTest {

    @Test
    void name() {
        Customer customer = new Customer();
        customer.setId(3L);
        customer.setFirstName("sohyeon");
        customer.setLastName("kim");

        Customer customer1 = new Customer(1L, "sohyeon", "kim");

        Customer customer2 = Customer.builder()
                .id(2L)
                .firstName("sohyeon")
                .lastName("kim")
                .build();
    }
}
