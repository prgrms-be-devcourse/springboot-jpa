package com.springbootjpa.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
@Embeddable
public class Delivery {

    private String roadAddress;

    private String detailAddress;

    public Delivery(String roadAddress, String detailAddress) {
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
    }
}
