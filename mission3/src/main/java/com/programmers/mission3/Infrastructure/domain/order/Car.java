package com.programmers.mission3.Infrastructure.domain.order;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CAR")
@Getter
public class Car extends Item{
    private long power;
}
