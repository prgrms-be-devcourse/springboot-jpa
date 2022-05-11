package com.prgms.springbootjpa.domain.order.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FURNITURE")
public class Furniture extends Item {

    private int weight;
    private int height;
}
