package com.example.jpalecture.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@DiscriminatorValue("Furniture")
@Entity
public class Furniture extends Item {
    private int width;
    private int height;
}
