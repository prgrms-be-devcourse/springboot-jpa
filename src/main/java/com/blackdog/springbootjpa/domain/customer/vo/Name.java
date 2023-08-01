package com.blackdog.springbootjpa.domain.customer.vo;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Name {
    private String nameValue;
}
