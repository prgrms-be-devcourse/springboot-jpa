package com.prgms.springbootjpa.domain.order.item;

import com.prgms.springbootjpa.domain.order.vo.Price;
import com.prgms.springbootjpa.domain.order.vo.Quantity;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FURNITURE")
public class Furniture extends Item {

    private int weight;
    private int height;

    protected Furniture() {
    }

    public Furniture(Price price,
        Quantity stockQuantity, int weight, int height) {
        super(price, stockQuantity);
        this.weight = weight;
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }
}
