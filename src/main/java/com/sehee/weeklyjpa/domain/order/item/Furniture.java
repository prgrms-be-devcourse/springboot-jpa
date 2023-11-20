package com.sehee.weeklyjpa.domain.order.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("FURNITURE")
public class Furniture extends Item {
    private long width;
    private long height;
}
