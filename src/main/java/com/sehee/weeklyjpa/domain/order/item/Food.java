package com.sehee.weeklyjpa.domain.order.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Food extends Item {
    private String chef;
}
