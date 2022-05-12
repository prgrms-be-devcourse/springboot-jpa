package com.example.jpalecture.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@DiscriminatorValue("CAR")
@Entity
public class Car extends Item {
    private int power;
}
