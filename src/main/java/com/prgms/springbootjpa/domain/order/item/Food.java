package com.prgms.springbootjpa.domain.order.item;

import com.prgms.springbootjpa.domain.order.vo.Price;
import com.prgms.springbootjpa.domain.order.vo.Quantity;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FOOD")
public class Food extends Item {

    private String chef;

    protected Food() {
    }

    public Food(Price price,
        Quantity stockQuantity, String chef) {
        super(price, stockQuantity);
        this.chef = chef;
    }

    public String getChef() {
        return chef;
    }
}
