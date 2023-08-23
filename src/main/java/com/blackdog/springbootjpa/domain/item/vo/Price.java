package com.blackdog.springbootjpa.domain.item.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@RequiredArgsConstructor
@Embeddable
public class Price {
    @Positive
    @Column(name = "price", nullable = false)
    private long price;
}
