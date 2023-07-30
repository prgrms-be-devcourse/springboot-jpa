package com.blackdog.springbootjpa.domain.customer.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Age {
    @Column(name = "age", length = 3, nullable = false)
    private int ageValue;
}
