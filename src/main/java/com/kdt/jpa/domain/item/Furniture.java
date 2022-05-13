package com.kdt.jpa.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@DiscriminatorValue("Furniture")
public class Furniture extends Item{
    private int width;
    private int height;
}
