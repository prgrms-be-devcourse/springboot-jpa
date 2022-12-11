package com.kdt.jpa.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorValue("FOOD")
public class Food extends Item{
    private String chef;
}