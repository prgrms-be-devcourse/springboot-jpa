package com.programmers.springbootjpa.domain.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Column(name = "street_address", nullable = false)
    private String streetAddress;
    @Column(name = "detailed_address", nullable = false)
    private String detailedAddress;
    @Column(name = "zip_code", nullable = false)
    private Integer zipCode;

    public Address(String streetAddress, String detailedAddress, Integer zipCode) {
        this.streetAddress = streetAddress;
        this.detailedAddress = detailedAddress;
        this.zipCode = zipCode;
    }
}
