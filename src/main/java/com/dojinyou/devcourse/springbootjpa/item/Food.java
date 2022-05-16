package com.dojinyou.devcourse.springbootjpa.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Food")
public class Food extends Item{

    private String chef;

    protected Food() {
        super();
    }

    public String getChef() {
        return chef;
    }

    public void setChef(String chef) {
        this.chef = chef;
    }

    private Food(Builder builder) {
        super(builder.price, builder.stockQuantity);
        this.chef = builder.chef;
    }

    public static class Builder {

        private Long price;

        private Long stockQuantity;

        private String chef;

        public Builder() {
        }

        public Builder price(long price) {
            this.price = price;
            return this;
        }

        public Builder stockQuantity(long stockQuantity) {
            this.stockQuantity = stockQuantity;
            return this;
        }

        public Builder chef(String chef) {
            this.chef = chef;
            return this;
        }

        public Item builder() {
            return new Food(this);
        }
    }
}
