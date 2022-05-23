package com.kdt.jpa.order.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("FOOD")
@Getter @Setter
public class Food extends Item{
    private String chef;
}
