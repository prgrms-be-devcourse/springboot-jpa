package com.kdt.jpa.order.domain;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("CAR")
@Getter @Setter
public class Car extends Item{
    private int power;
}
