package com.kdt.jpaproject.domain.order.model.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue("Car")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Car extends Item {
    @Column(name = "power")
    private int power;
}
