package com.programmers.springbootjpa.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "zipcode", nullable = false, length = 10)
    private String zipcode;


    public Address(String address, String zipcode) {
        this.address = address;
        this.zipcode = zipcode;
    }

    protected Address() {}
}
