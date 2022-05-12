package com.part4.jpa2.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter @Setter
@DiscriminatorValue("CAR")
public class Car extends Item{
    private int power;
}
