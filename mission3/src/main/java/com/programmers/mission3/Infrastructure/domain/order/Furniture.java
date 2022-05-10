package com.programmers.mission3.Infrastructure.domain.order;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue("FURNITURE")
public class Furniture extends Item{
    private long width;
    private long height;
}
