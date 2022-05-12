package com.kdt.jpaproject.domain.order.model.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue("Food")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food extends Item {
    @Column(name = "chef")
    private String chef;
}
