package com.prgms.springbootjpa.domain.order.item;

import com.prgms.springbootjpa.domain.order.vo.Price;
import com.prgms.springbootjpa.domain.order.vo.Quantity;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CAR")
public class Car extends Item {

    private int power;

    protected Car() {
    }
    
    public Car(Price price,
        Quantity stockQuantity, int power) {
        super(price, stockQuantity);
        this.power = power;
    }

    public int getPower() {
        return power;
    }
}
