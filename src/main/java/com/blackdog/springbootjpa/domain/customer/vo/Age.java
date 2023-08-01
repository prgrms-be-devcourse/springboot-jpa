package com.blackdog.springbootjpa.domain.customer.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Embeddable
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Age {
    @Column(name = "age", length = 3, nullable = false)
    private int ageValue;
}
