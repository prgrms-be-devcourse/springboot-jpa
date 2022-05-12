package com.kdt.jpaproject.domain.order.model.item;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue("Food")
public class Food extends Item {
    @Column(name = "chef")
    private String chef;
}
