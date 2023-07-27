package com.example.prog.orderingsystem.customer;

public class CustomerFactory {

    public static Customer getNewCustomer() {
        return  Customer.builder()
                .firstName("Seyeon")
                .lastName("Park")
                .build();
    }
}
