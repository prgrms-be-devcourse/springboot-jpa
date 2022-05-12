package com.kdt.jpaproject.domain.order.model.item;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue("Car")
public class Car extends Item {
    @Column(name = "power")
    private int power;
}
