package com.pppp0722.springbootjpa.domain.order;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("FURNITURE")
public class Furniture extends Item {

    @Column(name = "width", nullable = true)
    private int width;

    @Column(name = "height", nullable = true)
    private int height;
}