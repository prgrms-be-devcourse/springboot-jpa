package com.kdt.jpaproject.domain.order.model.item;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue("Furniture")
public class Furniture extends Item {
    @Column(name = "width")
    private int width;
    @Column(name = "height")
    private int height;
}
