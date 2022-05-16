package com.dojinyou.devcourse.springbootjpa.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Car")
public class Car extends Item {
    private long power;

    protected Car() {
        super();
    }

    public long getPower() {
        return power;
    }

    public void setPower(long power) {
        this.power = power;
    }

    private Car(Builder builder) {
        super(builder.id, builder.price, builder.stockQuantity);
        this.power = builder.power;
    }

    public static class Builder {

        private Long id;

        private Long price;

        private Long stockQuantity;

        private long power;

        public Builder() {
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder price(long price) {
            this.price = price;
            return this;
        }

        public Builder stockQuantity(long stockQuantity) {
            this.stockQuantity = stockQuantity;
            return this;
        }

        public Builder power(long power) {
            this.power = power;
            return this;
        }

        public Item builder() {
            if ((price == null) || (stockQuantity == null) || power == 0 ) {
                throw new IllegalArgumentException();
            }
            return new Car(this);
        }
    }
}
