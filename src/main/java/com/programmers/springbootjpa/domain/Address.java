package com.programmers.springbootjpa.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String streetAddress;
    private String detailedAddress;
    private Integer zipCode;
}
