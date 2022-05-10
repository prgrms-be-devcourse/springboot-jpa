package com.programmers.mission3.Infrastructure.domain.order;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FOOD")
@Getter
public class Food extends Item{
    private String chef;
}
