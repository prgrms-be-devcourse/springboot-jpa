package com.prgms.springbootjpa.domain.order.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FOOD")
public class Food extends Item {

    private String chef;
}
