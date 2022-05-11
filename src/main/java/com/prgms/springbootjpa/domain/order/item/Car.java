package com.prgms.springbootjpa.domain.order.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CAR")
public class Car extends Item {

    private int power;
}
