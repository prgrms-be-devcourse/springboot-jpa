package com.part4.jpa2.domain.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter @Setter
@DiscriminatorValue("FURNITURE")
public class Furniture extends Item{
    private int width;
    private int height;
}
