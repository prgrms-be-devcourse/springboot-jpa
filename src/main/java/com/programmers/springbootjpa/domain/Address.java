package com.programmers.springbootjpa.domain;


import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String address;

    private String zipcode;


    public Address(String address, String zipcode) {
        this.address = address;
        this.zipcode = zipcode;
    }

    protected Address() {}
}
