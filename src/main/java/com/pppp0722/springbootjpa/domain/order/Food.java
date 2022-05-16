package com.pppp0722.springbootjpa.domain.order;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("FOOD")
public class Food extends Item {

    @Column(name = "chef", nullable = true)
    private String chef;
}