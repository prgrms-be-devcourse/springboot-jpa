package com.kdt.lecture.domain.item;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue("CAR")
public class Car extends Item{

    private int power;

}
