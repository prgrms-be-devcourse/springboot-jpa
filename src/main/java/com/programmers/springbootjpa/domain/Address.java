package com.programmers.springbootjpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    @Column(name = "street_address", nullable = false)
    private String streetAddress;
    @Column(name = "detailed_address", nullable = false)
    private String detailedAddress;
    @Column(name = "zip_code", nullable = false)
    private Integer zipCode;


}
