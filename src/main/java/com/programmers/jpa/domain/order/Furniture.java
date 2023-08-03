package com.programmers.jpa.domain.order;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@DiscriminatorValue("FURNITURE")
public class Furniture extends Item{
    private int width;
    private int height;
}
