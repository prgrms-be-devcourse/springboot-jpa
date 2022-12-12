package com.prgrms.be.jpa.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("FURNITURE")
public class Furniture extends Item {
    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    public Furniture(int price, int stockQuantity, int width, int height) {
        super(price, stockQuantity);
        this.width = width;
        this.height = height;
    }
}
