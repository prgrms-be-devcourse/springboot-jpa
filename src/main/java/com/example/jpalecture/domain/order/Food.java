package com.example.jpalecture.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@DiscriminatorValue("FOOD")
@Entity
public class Food extends Item {
    private String chef;
}
