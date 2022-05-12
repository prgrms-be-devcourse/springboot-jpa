package com.kdt.jpaproject.domain.order.model.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@DiscriminatorValue("Furniture")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Furniture extends Item {
    @Column(name = "width")
    private int width;
    @Column(name = "height")
    private int height;
}
