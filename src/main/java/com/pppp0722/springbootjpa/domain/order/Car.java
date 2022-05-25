package com.pppp0722.springbootjpa.domain.order;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("CAR")
public class Car extends Item {

    @Column(name = "power", nullable = true)
    private int power;
}
