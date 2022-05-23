package com.kdt.jpa.order.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("FUNITURE")
@Getter @Setter
public class Furniture extends Item {
    private int width;
    private int height;
}
